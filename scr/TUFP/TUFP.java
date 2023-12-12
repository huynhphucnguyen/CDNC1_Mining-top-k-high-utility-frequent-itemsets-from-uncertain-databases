package TUFP;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import cupList.*;
import utility.*;

/**
 * TUFP
 */
public class TUFP{
    public static int k = 6; //top - k
    public List<topK<String, Double>> topKUFP;
    public List<Cup<String, Integer, Double>> cupl;
    
    
    public void readData(String filePath) {
        try {
            File file = new File(filePath);
            Scanner scanner = new Scanner(file);
            List<List<Double>> existensialProbability = new ArrayList<>();

            String[] itemName = scanner.nextLine().split(" "); //name intem in 1st line
            // Convert it to a List<String>
            List<String> itemList = new ArrayList<>(); //This is list of item's name
            for (String item : itemName) {
                itemList.add(item);
            }
            while (scanner.hasNextLine()) { //get list of ep's list
                String line = scanner.nextLine();
                String[] values = line.split(" ");
                List<Double> eps = new ArrayList<>();  
                for (int i = 0; i < values.length; i++) {
                    String valueStr = values[i];
                    Double ep = null;
                    ep = Double.parseDouble(valueStr); //String to Duoble
                    eps.add(ep);
                }
                existensialProbability.add(eps); // list of ep
            }
            cupList<String, Integer, Double> cupTempt = new cupList<>();
            cupl = cupTempt.createCupList(itemList, existensialProbability); // cuplist
            scanner.close();
            
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + e.getMessage());
        }
    }
    // public void TUFP(){
        // readData(filePath);

    // }

    // public void TUFPSearch(cupList<T> cup, cupList<T> currentCup, List<topK<T>> topKUFP){
        
    // }




    @Override
    public String toString() {
        return "top " + k + " UFPs:\n" + topKUFP;
    }
    
}