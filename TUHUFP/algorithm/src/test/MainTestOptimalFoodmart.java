package test;

import main.AlgorithmTUHUFP;
import optimal.TUHUFPOptimal;

public class MainTestOptimalFoodmart {

    public static void main(String[] args) {
        //get path of data file
        String filePath =  "../../data/input_foodmart.txt, ../../data/foodmart_utility.txt";

        //the number of UHUFPs
        int k = 900;
        double percentage = 0.000115;

        System.out.println("Algorithm is running on Foodmart");

        // Applying the TUHUFP algorithm
        TUHUFPOptimal<String, Integer, Double> tuhufp = new TUHUFPOptimal<>();
        tuhufp.runTUHUFPAlgorithm(filePath, percentage, k);

        System.out.println("===================================================");
        System.out.println("Algorithm finished and result is saved in file 'output_FoodmartOptimal.txt' ");

        tuhufp.printStats("../../out/output_FoodmartOptimal.txt");
    }
}
