package test;

import main.AlgorithmTUFP;

import java.util.Scanner;

public class MainTestTUFPT10I4D100K {

    public static void main(String[] args) {

        //get path of data file
        String filePath =  "../../data/input_T10I4D100K.txt";

        Scanner scanner = new Scanner(System.in);
        // Taking input as String
        System.out.print("Choose top-k of UFPs: ");
        String inputString = scanner.nextLine();

        //the number of UFPs
        int k = Integer.parseInt(inputString);


        System.out.println("Algorithm is running . . .");

        //// Applying the TUFP algorithm
        AlgorithmTUFP<String, Integer, Double> tufp = new AlgorithmTUFP<>();
        tufp.runTUFPAlgorithm(filePath, k);

        System.out.println("===================================================");
        System.out.println("Algorithm finished and result is saved in file 'output_T10I4D100K.txt' ");

        tufp.printStats("../../out/output_T10I4D100K.txt");
    }

}
