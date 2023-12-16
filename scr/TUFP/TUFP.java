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
    public Double threshold = 0.0;
    
    
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
   


    public void TUFPSearch(List<Cup<String, Integer, Double>> currentCup){
        Double overestimate = currentCup.get(0).getExpSupOfPattern()*currentCup.get(1).getMax();
        
        if(overestimate < threshold){
            return;
        }else{
            for(int i=0; i<currentCup.size()-1; i++){
                List<Cup<String, Integer, Double>> newCupList = new ArrayList<>();
                for(int j=i+1; j<currentCup.size(); j++){
                    mathFormulas<Integer, Double> math = new mathFormulas<>();
                    Double expSupMin = math.expSup(null)
                    if (currentCup.get(i).getExpSupOfPattern()*currentCup.get(j).getMax()) {
                        
                    }
                }
            }
        }

        TUFPSearch()
    }

     public void TUFP(String filePath/*, int k*/){ 
        readData(filePath);
        topKUFP = new ArrayList<>();
        List<Cup<String, Integer, Double>> currentCup = new ArrayList<>(); 
        for(int i=0; i<k; i++){
            topKUFP.add(new topK<String,Double>(cupl.get(i).getNamePattern(),cupl.get(i).getExpSupOfPattern()));
            currentCup.add(cupl.get(i));
        }
        threshold = topKUFP.get(topKUFP.size()-1).getExpSupOfPattern();
        TUFPSearch(currentCup);
    }




    @Override
    public String toString() {
        return "top " + k + " UFPs:\n" + topKUFP;
    }
    
}