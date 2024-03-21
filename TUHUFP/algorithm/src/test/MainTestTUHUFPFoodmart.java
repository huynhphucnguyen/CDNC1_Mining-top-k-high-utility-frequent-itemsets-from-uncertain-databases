package test;

import main.AlgorithmTUHUFP;

import java.util.Scanner;

public class MainTestTUHUFPFoodmart {

    public static void main(String[] args) {
        //get path of data file
        String filePath =  "../../data/input_foodmart.txt, ../../data/foodmart_utility.txt";

        Scanner scanner = new Scanner(System.in);
        // Taking k
        System.out.print("k: ");
        String input1 = scanner.nextLine();
        // Taking Minimum Utility
        System.out.print("Minimum Utility: ");
        String input2 = scanner.nextLine();

        //the number of UHUFPs
        int k = Integer.parseInt(input1);
        double minUtil = Double.parseDouble(input2);

        System.out.println("Algorithm is running on Foodmart");

        // Applying the TUHUFP algorithm
        AlgorithmTUHUFP<String, Integer, Double> tuhufp = new AlgorithmTUHUFP<>();
        tuhufp.runTUHUFPAlgorithm(filePath, minUtil, k);

        System.out.println("===================================================");
        System.out.println("Algorithm finished and result is saved in file 'output_Foodmart.txt' ");

        tuhufp.printStats("../../out/output_Foodmart.txt");
    }
}
