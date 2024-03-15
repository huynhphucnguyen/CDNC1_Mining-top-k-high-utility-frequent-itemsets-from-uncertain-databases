package test;

import main.AlgorithmTUHUFP;

public class MainTestTUHUFPRetail {

    public static void main(String[] args) {
        //get path of data file
        String filePath =  "../../data/input_retail.txt, ../../data/retail_utility.txt";

        //the number of UHUFPs
        int k = 900;
        double percentage = 0.00045;

        System.out.println("Algorithm is running on Retail");

        // Applying the TUHUFP algorithm
        AlgorithmTUHUFP<String, Integer, Double> tuhufp = new AlgorithmTUHUFP<>();
        tuhufp.runTUHUFPAlgorithm(filePath, percentage, k);

        System.out.println("===================================================");
        System.out.println("Algorithm finished and result is saved in file 'output_Retail.txt' ");

        tuhufp.printStats("../../out/output_Retail.txt");
    }

}
