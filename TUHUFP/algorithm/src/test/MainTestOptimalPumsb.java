package test;

import optimal.TUHUFPOptimal;

import java.util.Scanner;

public class MainTestOptimalPumsb {
    public static void main(String[] args) {
        //get path of data file
        String filePath =  "../../data/input_pumsb.txt, ../../data/pumsb_utility.txt";

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

        System.out.println("Algorithm is running on Pumsb");

        //// Applying the TUHUFP algorithm
        TUHUFPOptimal<String, Integer, Double> tuhufp = new TUHUFPOptimal<>();
        tuhufp.runTUHUFPAlgorithm(filePath, minUtil, k);

        System.out.println("===================================================");
        System.out.println("Algorithm finished and result is saved in file 'output_PumsbOptimal.txt' ");

        tuhufp.printStats("../../out/output_PumsbOptimal.txt");
    }
}
