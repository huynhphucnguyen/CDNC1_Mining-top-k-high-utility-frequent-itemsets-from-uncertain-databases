package test;

import optimal.TUHUFPOptimal;

public class MainTestOptimalChess {
    public static void main(String[] args) {
        //get path of data file
        String filePath =  "../../data/input_Chess.txt, ../../data/chess_utility.txt";

        //the number of UHUFPs
        int k = 900;
        double percentage = 0.22;

        System.out.println("Algorithm is running on Chess");

        //// Applying the TUHUFP algorithm
        TUHUFPOptimal<String, Integer, Double> tuhufp = new TUHUFPOptimal<>();
        tuhufp.runTUHUFPAlgorithm(filePath, percentage, k);

        System.out.println("===================================================");
        System.out.println("Algorithm finished and result is saved in file 'output_ChessOptimal.txt' ");

        tuhufp.printStats("../../out/output_ChessOptimal.txt");
    }
}
