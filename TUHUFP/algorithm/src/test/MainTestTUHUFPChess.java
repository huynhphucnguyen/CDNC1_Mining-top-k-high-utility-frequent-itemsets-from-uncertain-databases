package test;

import main.AlgorithmTUHUFP;

public class MainTestTUHUFPChess {

    public static void main(String[] args) {
        //get path of data file
        String filePath =  "../../data/input_Chess.txt, ../../data/chess_utility.txt";

        //the number of UHUFPs
        int k = 900;
        double percentage = 0.22;

        System.out.println("Algorithm is running on Chess");

        //// Applying the TUHUFP algorithm
        AlgorithmTUHUFP<String, Integer, Double> tuhufp = new AlgorithmTUHUFP<>();
        tuhufp.runTUHUFPAlgorithm(filePath, percentage, k);

        System.out.println("===================================================");
        System.out.println("Algorithm finished and result is saved in file 'output_Chess.txt' ");

        tuhufp.printStats("../../out/output_Chess.txt");
    }

}
