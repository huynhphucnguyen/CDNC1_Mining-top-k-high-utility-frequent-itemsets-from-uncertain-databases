package test;

import main.AlgorithmTUFP;

import java.util.Scanner;

public class MainTestTUFPChess {

    public static void main(String[] args) {

        //get path of data file
        String filePath =  /*"../../data/input_Chess.txt";*/ "../../data/input_Chess.txt";

        Scanner scanner = new Scanner(System.in);
        // Taking input as String
        System.out.print("Choose top-k of UFPs: ");
        String inputString = scanner.nextLine();

        //the number of UFPs
        int k = Integer.parseInt(inputString);


        System.out.println("Algorithm is running on Chess");

        //// Applying the TUFP algorithm
        AlgorithmTUFP<String, Integer, Double> tufp = new AlgorithmTUFP<>();
        tufp.runTUFPAlgorithm(filePath, k);

        System.out.println("===================================================");
        System.out.println("Algorithm finished and result is saved in file 'output_Chess.txt' ");

        //tufp.printStats("../../out/output_Chess.txt");
        tufp.printStats("../../out/output_Chess.txt");
    }

}
