import oracle.jrockit.jfr.jdkevents.throwabletransform.ConstructorTracerWriter;
import sun.util.locale.provider.FallbackLocaleProviderAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class WTA {

    ArrayList<Weapon> weapons = new ArrayList<Weapon>();
    Integer numTargets = 0;
    ArrayList<Double> threats = new ArrayList<Double>();
    Double[][] weaponsProb;

    public ArrayList<Chromosome> initializePopulation(int populationSize, ArrayList<Weapon> weapons){
        int numWeapons = 0;
        for (int i=0;i<weapons.size();++i){
            numWeapons += weapons.get(i).num;
        }
        ArrayList<Chromosome> chromosomes = new ArrayList<Chromosome>();
        for (int i = 0; i < populationSize; i++) {
            chromosomes.add(new Chromosome(numWeapons, numTargets));
        }
        return chromosomes;
    }

    public void getInputs() {
        Scanner sc = new Scanner(System.in);

        //Take weapons types from user:
        System.out.println("Enter the weapon types and the number of instances of each type: (Enter “x” when you’re done)");
        String line = sc.nextLine();
        while(!line.matches("x")){
            String[] words = line.split(" ");
            weapons.add(new Weapon(words[0],Integer.parseInt(words[1])));
            line = sc.nextLine();
        }

        //Take number of targets:
        System.out.println("Enter the number of targets:");
        numTargets = sc.nextInt();

        //Take the threat coefficient of each target:
        System.out.println("Enter the threat coefficient of each target:");
        for(int i=0;i<numTargets;++i){
            threats.add(sc.nextDouble());
        }

        //Take the weapons’ success probabilities matrix:
        weaponsProb = new Double[weapons.size()][numTargets];
        System.out.println("Enter the weapons’ success probabilities matrix:");
        for(int i=0;i<weapons.size();++i){
            for(int j=0;j<numTargets;++j){
                weaponsProb[i][j] = sc.nextDouble();
            }
        }
        /*
        Inputs taken:
            weapons
            numTargets
            threats
            weaponsProb
         */
    }

    public ArrayList<Integer> rouletteWheelSelection(int numberOfParents, ArrayList<Chromosome> chromosomes){
        ArrayList<Integer> selected = new ArrayList<Integer>();
        double[] comulativeFitness = new double[chromosomes.size()];
        double last = 0,totFitness=0;
        for (int i = 0; i < chromosomes.size(); i++) {
            totFitness+=chromosomes.get(i).fitness;
        }
        for (int i = 0; i < chromosomes.size(); i++) {
            last += (chromosomes.get(i).fitness / totFitness);
            comulativeFitness[i] = last;
        }
        Boolean picked = true;
        for (int i = 0; i < numberOfParents; i++) {
            double r = Math.random();
            if(!picked) --i;
            else picked = false;
            for (int j = 0; j < chromosomes.size(); j++) {
                if(r<comulativeFitness[j] ){
                    if(selected.isEmpty() || selected.get(selected.size() - 1) != j){
                        selected.add(j);
                        picked = true;
                        break;
                    }
                }
            }
        }
        return selected;
    }

    public ArrayList<Chromosome> singlePointCrossover(double Pc, ArrayList<Chromosome> chromosomes, ArrayList<Integer> selectedIndices){
        ArrayList<Chromosome> newChromosomes = new ArrayList<Chromosome>();
        for (int i = 0; i < chromosomes.size(); i++) {
            Chromosome tempC = chromosomes.get(i);
            int[] copy_Array = Arrays.copyOf(tempC.genes,tempC.numGenes);
            newChromosomes.add(new Chromosome(tempC.numGenes, copy_Array, tempC.fitness));
        }
        Random rand = new Random();
        int Xc = rand.nextInt(chromosomes.get(0).numGenes-1)+1;
        for (int i = 0; i < selectedIndices.size(); i+=2) {
            double Rc = Math.random();
            if(Rc<Pc || Rc==Pc){
                int[] temp1 = newChromosomes.get(selectedIndices.get(i)).genes;
                int[] temp2 = newChromosomes.get(selectedIndices.get(i+1)).genes;
                for (int j = Xc; j < temp1.length; j++) {
                    int temp = temp1[j];
                    temp1[j] = temp2[j];
                    temp2[j] = temp;
                }
                newChromosomes.get(selectedIndices.get(i)).calFitness(weaponsProb, weapons, numTargets, threats);
                newChromosomes.get(selectedIndices.get(i+1)).calFitness(weaponsProb, weapons, numTargets, threats);
            }
        }
        return newChromosomes;
    }

    public ArrayList<Chromosome> mutation(double Pm, ArrayList<Chromosome> newC, ArrayList<Integer> selectedIndices, int numTargets){
        for (int i = 0; i < selectedIndices.size(); i++) {
            for (int j = 0; j < newC.get(0).genes.length; j++) {
                double r = Math.random();
                if(r<Pm || r==Pm){
                    Random rand = new Random();
                    int newG = rand.nextInt(numTargets)+1;
                    if(newC.get(selectedIndices.get(i)).genes[j]==newG){
                        j--;
                    }
                    else{
                        newC.get(selectedIndices.get(i)).genes[j] = newG;
                        newC.get(selectedIndices.get(i)).calFitness(weaponsProb, weapons, numTargets, threats);
                    }
                }
            }
        }
        return newC;
    }

    public void printAllC(ArrayList<Chromosome> arr){
        System.out.println("------------------------------");
        for (int i = 0; i < arr.size(); i++) {
            System.out.print("C"+ (i+1) +" : ");
            arr.get(i).print();
        }
        System.out.println("------------------------------");
    }

    public void getBest(ArrayList<Chromosome> chrList, ArrayList<Weapon> weapons){
        int fittestIndex = 0;
        for (int i = 0; i < chrList.size(); i++) {
            if(chrList.get(i).fitness < chrList.get(fittestIndex).fitness){
                fittestIndex = i;
            }
        }
        int genIndex = 0;
        System.out.println("The final WTA solution is:");
        for (int i = 0; i < weapons.size(); i++) {
            for (int j = 0; j < weapons.get(i).num; j++) {
                System.out.println(weapons.get(i).weaponName +" #"+(j+1)+" assigned to target #" + chrList.get(fittestIndex).genes[genIndex]+",");
                genIndex++;
            }
        }
        System.out.println("\nThe expected total threat of the surviving targets is "+chrList.get(fittestIndex).fitness);
    }
}
