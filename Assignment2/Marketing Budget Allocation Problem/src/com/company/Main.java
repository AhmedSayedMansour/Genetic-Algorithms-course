package com.company;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Float marketingTarget;
        int numberOfChannels;
        ArrayList<String> channelsNames = new ArrayList<>();
        ArrayList<Float> rois = new ArrayList<>();
        ArrayList<Float> lower = new ArrayList<>();
        ArrayList<Float> upper = new ArrayList<>();
        String line;

        Scanner numberInput = new Scanner(System.in);
        Scanner lineInput = new Scanner(System.in);

        System.out.println("Enter the marketing budget (in thousands):");
        marketingTarget = numberInput.nextFloat();

        System.out.println("Enter the number of marketing channels:");
        numberOfChannels = numberInput.nextInt();

        System.out.println("Enter the name and ROI (in %) of each channel separated by space:");
        for(int i=0 ; i< numberOfChannels ; i++)
        {
            line = lineInput.nextLine();
            String[] temp = line.split(" ");
            channelsNames.add(temp[0]);
            rois.add(Float.parseFloat(temp[1]));
        }

        System.out.println("Enter the lower (k) and upper bounds (%) of investment in each channel: (enter x if there is no bound)");
        for(int i=0 ; i< numberOfChannels ; i++)
        {
            line = lineInput.nextLine();
            String[] temp = line.split(" ");
            if(temp[0].equals("x")) lower.add(0.0f);
            else lower.add(Float.parseFloat(temp[0]));

            if(temp[1].equals("x")) upper.add(marketingTarget);
            else upper.add(Float.parseFloat(temp[1])*(marketingTarget/100));
        }

        //Solution Run

        Chromosome c = new Chromosome(numberOfChannels);
        ArrayList<Chromosome> generation;
        ArrayList<Float> generationsFitness = null;

        generation = c.initializeGeneration(50,numberOfChannels , lower , upper , marketingTarget);

        GA_Solution solution = new GA_Solution();

        for(int i=0 ; i<50 ; i++)
        {
            generationsFitness = solution.calculateFitness(generation, rois);
            solution.rouletteWheelSelection(generation ,generationsFitness);
            solution.towPointCrossover(numberOfChannels);
            solution.uniformMutation(numberOfChannels, lower, upper);
            solution.checkSelected(marketingTarget);
            solution.elitistReplacement(generation, generationsFitness, rois);
        }
        solution.showBest(generation, generationsFitness , channelsNames , rois);

    }
}
