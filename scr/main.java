import java.io.*;
import java.util.List;
import java.util.Scanner;

import TUFP.*;
import cupList.*;

/**use the TUFP algorithm
 * 
 * @author Huynh Phuc Nguyen, Ma Nhat Bien
 */

public class Main {
    public static void main(String[] args) {
        //get path of data file
        String filePath = /*"../database.txt" */ "../ep_retail.txt";
        //the number of UFPs
        int k = 900;

        //// Applying the TUFP algorithm
        TUFP tufp = new TUFP();
        tufp.runTUFP(filePath, k);
        try (PrintWriter outputWriter = new PrintWriter("../output.txt")) {
            for (topK<String, Double> t : tufp.topKUFP) {
                // Instead of printing to the console, write to the file
                outputWriter.println(t);
            }

            // Print the statistics to the console
            tufp.printStats();
        } catch (IOException e) {
            // Handle the exception (e.g., print an error message)
            e.printStackTrace();
        }
        // System.out.println(tufp.threshold);
    }
}