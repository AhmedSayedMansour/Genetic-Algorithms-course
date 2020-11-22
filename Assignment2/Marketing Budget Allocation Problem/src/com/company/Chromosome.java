package com.company;

import java.util.ArrayList;
import java.util.Random;

public class Chromosome {
    float[] genes;
    int numberOfGenes;

    Chromosome(int numGenes)
    {
        numberOfGenes = numGenes;
        genes = new float[numGenes];
    }

    public ArrayList<Chromosome> initializeGeneration(int generationSize , int numberOfGenes , ArrayList<Float>lower , ArrayList<Float>upper , Float investment)
    {
        ArrayList<Chromosome> generation = new ArrayList<>();
        Chromosome temp;
        Double totalInvestment = 0.0;
        Random rand = new Random();

        while (generation.size() <= generationSize)
        {
            temp = new Chromosome(numberOfGenes);
            for(int i=0 ; i<numberOfGenes ; i++)
            {
                float randFloat = rand.nextFloat()*(upper.get(i) - lower.get(i)) + lower.get(i);
                temp.genes[i] = randFloat;
                totalInvestment += randFloat;
            }
            if(totalInvestment <= investment) generation.add(temp);
            totalInvestment = 0.0;
        }
        return  generation;
    }

    public void show(ArrayList<Chromosome> generation , int numberOfGenes)
    {
        for(int i=0 ; i<generation.size() ; i++)
        {
            for(int j=0 ; j<numberOfGenes ; j++)
            {
                System.out.print(generation.get(i).genes[j] + " ");
            }
            System.out.println();
        }
    }
}
