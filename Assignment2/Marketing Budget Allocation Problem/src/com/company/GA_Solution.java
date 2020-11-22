package com.company;

import java.io.IOException;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Random;

public class GA_Solution {
    ArrayList<Float> fitness;
    ArrayList<Chromosome> selected;
    ArrayList<Integer> selectedIndices;
    Float totalFitness = 0.0f;

    public ArrayList<Float> calculateFitness(ArrayList<Chromosome> generations, ArrayList<Float> rois)
    {
        int numberOfGenes = generations.get(0).numberOfGenes;
        fitness = new ArrayList<>();

        for(int i=0 ; i<generations.size() ; i++)
        {
            for(int j=0 ; j< numberOfGenes ; j++)
            {
                totalFitness += rois.get(j) * (generations.get(i).genes[j]/100);
            }
            fitness.add(totalFitness);
            totalFitness = 0.0f;
        }
        return fitness;
    }

    public void rouletteWheelSelection(ArrayList<Chromosome> generations ,  ArrayList<Float> fitness)
    {
        selected = new ArrayList<>();
        selectedIndices = new ArrayList<>();

        for(int i=0 ; i<fitness.size()-1 ; i+=2)
        {
            if(fitness.get(i) > fitness.get(i+1))
            {
                selected.add(generations.get(i));
                selectedIndices.add(i);
            }
            else
            {
                selected.add(generations.get(i+1));
                selectedIndices.add(i+1);
            }
        }
    }

    public void towPointCrossover(int numberOfGenes)
    {
        Random rand = new Random();
        int i1 , i2;
        for(int i=0 ; i<selected.size()-1 ; i+=2)
        {
            i1 = rand.nextInt(numberOfGenes-1);
            i2 = rand.nextInt(numberOfGenes-1);
            if(i2 < i1)
            {
                int temp = i1;
                i1 = i2;
                i2 = temp;
            }
            for (int j=i1 ; j<=i2 ; j++)
            {
                float temp = selected.get(i).genes[j];
                selected.get(i).genes[j] = selected.get(i+1).genes[j];
                selected.get(i+1).genes[j] = temp;
            }
        }
    }

    public void uniformMutation(int numberOfGenes , ArrayList<Float> lower , ArrayList<Float> upper)
    {
        Random rand = new Random();
        float theta;
        float ri1 , ri2;
        boolean thetaL ,thetaU;

        for(int i=0 ; i<selected.size() ; i++)
        {
            for(int j=0 ; j<numberOfGenes ; j++)
            {
                thetaL = thetaU = false;
                ri1 = rand.nextFloat();
                if(ri1 <= 0.5)
                {
                    thetaL = true;
                    theta = selected.get(i).genes[j] - lower.get(j);
                }
                else
                {
                    thetaU = true;
                    theta = upper.get(j) - selected.get(i).genes[j];
                }

                ri2 = rand.nextFloat()*theta;

                if(thetaL) selected.get(i).genes[j] -= ri2;
                else if(thetaU) selected.get(i).genes[j] += ri2;
            }
        }
    }

    public void nonUniformMutation(int numberOfGenes , ArrayList<Float> lower , ArrayList<Float> upper)
    {
        Random rand = new Random();
        float theta;
        float ri1, r;
        boolean thetaL ,thetaU;

        for(int i=0 ; i<selected.size() ; i++)
        {
            for (int j = 0; j < numberOfGenes; j++)
            {
                thetaL = thetaU = false;
                ri1 = rand.nextFloat();

                if(ri1 <= 0.5)
                {
                    thetaL = true;
                    theta = selected.get(i).genes[j] - lower.get(j);
                }
                else
                {
                    thetaU = true;
                    theta = upper.get(j) - selected.get(i).genes[j];
                }

                r = rand.nextFloat();
                double power = 1 - (i/selected.size()*2);
                double addingValue = (float)(theta * ( 1 - (Math.pow(r,power))));

                if(thetaL) selected.get(i).genes[j] -= addingValue;
                else selected.get(i).genes[j] += addingValue;
            }
        }
    }

    /// Check and Fix Invisible Solutions
    public void checkSelected(float investment)
    {
        for(int i=0; i <selected.size() ; i++)
        {
            if(getTotal(selected.get(i)) > investment)
            {
                while (getTotal(selected.get(i)) > investment)
                {
                   int index = getBiggestIndex(selected.get(i));
                   selected.get(i).genes[index] -= 1;
                }
            }
        }
    }
    public Float getTotal(Chromosome chromosome)
    {
        int numberOfGenes = chromosome.numberOfGenes;
        float total = 0.0f;
        for(int i=0 ; i<numberOfGenes ; i++)
        {
            total += chromosome.genes[i];
        }
        return total;
    }
    public Integer getBiggestIndex(Chromosome chromosome)
    {
        int numberOfGenes = chromosome.numberOfGenes;
        float biggestNumber = 0.0f;
        int index = 0;
        for(int i=0 ; i<numberOfGenes ; i++)
        {
            if(chromosome.genes[i] > biggestNumber)
            {
                biggestNumber = chromosome.genes[i];
                index = i;
            }
        }
        return index;
    }
    ///-----------------------------------------------------------------------------------------------------------------

    public void elitistReplacement(ArrayList<Chromosome> generations, ArrayList<Float> generationsFitness, ArrayList<Float> rois)
    {
        int numberOfGenes = generations.get(0).numberOfGenes;
        ArrayList<Float> selectedFitness;
        selectedFitness = calculateFitness(selected, rois);

        for(int i=0 ; i<selected.size() ; i++)
        {
            if(generationsFitness.get(selectedIndices.get(i)) < selectedFitness.get(i))
            {
                for(int j=0; j<numberOfGenes ; j++)
                {
                    generations.get(selectedIndices.get(i)).genes[j] = selected.get(i).genes[j];
                }
            }
        }
    }

    public void showBest(ArrayList<Chromosome> generations, ArrayList<Float> generationsFitness, ArrayList<String> names, ArrayList<Float> rois, int iterate) throws IOException
    {
        int numberOfGenes = generations.get(0).genes.length;
        float bestFitness = 0.0f;
        int index = 0;
        for(int i=0 ; i<generations.size() ; i++)
        {
            if(generationsFitness.get(i) > bestFitness)
            {
                bestFitness = generationsFitness.get(i);
                index = i;
            }
        }

        System.out.println("Please wait while running the GA…\n" + "The final marketing budget allocation is:");

        Float totalProfit = 0.0f;
        String output = "";
        for(int j=0; j<numberOfGenes ; j++)
        {
            totalProfit += (generations.get(index).genes[j] / 100) * rois.get(j);
            System.out.println(names.get(j) + " -> " + generations.get(index).genes[j]+"K");
            output += names.get(j) + " -> " + generations.get(index).genes[j]+ "K" + "\n";
        }
        System.out.println("The total profit is : " + totalProfit + "K" + "\n\n");

        ///Write To File
        output += "The total profit is : " + totalProfit+ "K" + "\n\n\n";
        WriteToFile file = new WriteToFile();
        if(iterate == 1) file.clear();
        file.write(output , iterate);
    }

}
