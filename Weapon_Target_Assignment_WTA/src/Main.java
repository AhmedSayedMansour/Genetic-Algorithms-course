import com.sun.xml.internal.ws.api.model.wsdl.WSDLOutput;

import java.util.ArrayList;

public class Main {
    public static void runGeneticAlgorithm(int numOfGenerations) {
        WTA wta = new WTA();
        wta.getInputs();
        ArrayList<Chromosome> chromosomes = wta.initializePopulation(10, wta.weapons);
        for (int i = 0; i < chromosomes.size(); i++) {
            chromosomes.get(i).calFitness(wta.weaponsProb, wta.weapons, wta.numTargets, wta.threats);
        }
        //wta.printAllC(chromosomes);
        for (int i = 0; i < numOfGenerations; i++) {
            ArrayList<Integer> selectedIndices = wta.rouletteWheelSelection(4,chromosomes);
            /*for (int j = 0; j < selectedIndices.size(); j++) {
                System.out.println(selectedIndices.get(j));
            }*/
            ArrayList<Chromosome> newChromosomes = wta.singlePointCrossover(0.6, chromosomes,selectedIndices);
            //wta.printAllC(newChromosomes);
            //wta.printAllC(chromosomes);
            newChromosomes = wta.mutation(0.1, newChromosomes, selectedIndices, wta.numTargets);
            //General Replacement
            chromosomes = newChromosomes;
        }
        //wta.printAllC(chromosomes);
        System.out.println("\nPlease wait while running the GA…\n");
        wta.getBest( chromosomes, wta.weapons);
    }

    public static void main(String[] args) {
        runGeneticAlgorithm(5);
    }
}
