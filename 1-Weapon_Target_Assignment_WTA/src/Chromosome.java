import java.util.ArrayList;
import java.util.Random;

public class Chromosome {
    public int numGenes;
    public int[] genes;
    public double fitness;

    public Chromosome(int numGenes, int numTargets) {
        this.numGenes = numGenes;
        Random rand = new Random();
        genes = new int[numGenes];
        for (int i = 0; i < numGenes; i++) {
            genes[i] = rand.nextInt(numTargets) + 1;
        }
    }

    public Chromosome(int numGenes, int[] genes) {
        this.numGenes = numGenes;
        this.genes = genes;
    }

    public Chromosome(int numGenes, int[] genes, double fitness) {
        this.numGenes = numGenes;
        this.genes = genes;
        this.fitness = fitness;
    }

    public void print(){
        for (int i = 0; i < numGenes; i++) {
            System.out.print(genes[i]+" ");
        }
        System.out.println("  fitness = "+fitness);
    }

    public void calFitness(Double[][] weaponsProb, ArrayList<Weapon> weapons, int numTargets, ArrayList<Double> threats){
        double sum=0;
        for (int i = 0; i < numTargets; i++) {
            int weaponsIndex = 0;
            double tempSum = threats.get(i);
            for (int j = 0; j < numGenes; ) {
                for (int k = 0; k < weapons.get(weaponsIndex).num; k++) {
                    tempSum = tempSum * Math.pow((1-weaponsProb[weaponsIndex][genes[j]-1]),genes[j]==i+1?1:0);
                    j++;
                }
                weaponsIndex+=1;
            }
            sum+=tempSum;
        }
        fitness = sum;
    }
}
