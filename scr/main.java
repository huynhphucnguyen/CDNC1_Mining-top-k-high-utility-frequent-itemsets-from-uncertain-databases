import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
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
        String filePath = /*"../database.txt" */ "../Foodmart_ep.txt";
        //the number of UFPs
        int k = 100;

        //// Applying the TUFP algorithm
        TUFP tufp = new TUFP();
        tufp.TUFP(filePath, k);
        List<Cup<String, Integer, Double>> cupls = tufp.cupl;
        // for(Cup<String, Integer, Double> pattern : cupls){
        //     System.out.println(pattern); //print list of cup (Cup_List)
        // }

        //write the result into txt file
        // try (BufferedWriter writer = new BufferedWriter(new FileWriter(new File(docPath, "WriteLines.txt")))) {
        //     for (String line : lines) {
        //         writer.write(line);
        //         writer.newLine(); // Add a newline after each line
        //     }
        // } catch (IOException e) {
        //     e.printStackTrace();
        // }
        for(topK<String, Double> t:tufp.topKUFP){
            System.out.println(t); // print top-k UFP
        }
        tufp.printStats();
        // System.out.println(tufp.threshold);
    }
}