package TUFP;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

import cupList.*;
import utility.*;

/**
 * TUFP
 */
public class TUFP{
    public List<topK<String, Double>> topKUFP;
    public List<Cup<String, Integer, Double>> cupl;
    public Double threshold = 0.0;
    public mathFormulas<Integer, Double> math = new mathFormulas<>();
    
    
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
   

    //method to combine two of cup XY
    public Cup<String, Integer, Double> combineCup(Cup<String, Integer, Double> cupX, Cup<String, Integer, Double> cupY){
        List<Tep<Integer, Double>> tepListXY = new ArrayList<>();
        
        //combine name cupX with the last character of name cupY --> name CupXY
        String nameXY = cupX.getNamePattern() + cupY.getNamePattern().charAt(cupY.getNamePattern().length()-1);
        
        //cal expSup of cupXY
        Double expSup = math.expSupXY(cupX.getTEPList(),cupY.getTEPList());
        Double maxXY = 0.0;
        
        int i = 0;
        for(Tep<Integer, Double> t: cupX.getTEPList()){
            //compare TID to check if same TID, then add transaction into tepListXY
            if(t.getTID().compareTo(cupY.getTEPList().get(i).getTID()) == 0){  
                Double tepXY =  t.getExistensialProbability()*cupY.getTEPList().get(i).getExistensialProbability();
                if(tepXY>maxXY){
                    maxXY = tepXY;
                }
                tepListXY.add(new Tep<Integer, Double>(t.getTID(),tepXY));
                i++;
            }
        }

        return new Cup<String,Integer,Double>(nameXY, expSup, tepListXY, maxXY);
    }


    //method combine two of CUP then search top-k UFP
    public void TUFPSearch(List<Cup<String, Integer, Double>> currentCup){
        /*for(Cup<String, Integer, Double> c:currentCup){
            System.out.println(c);
        }
        System.out.println("--------------------------------------");*/
        Double overestimate = currentCup.get(0).getExpSupOfPattern()*currentCup.get(1).getMax();
        
        if(overestimate < threshold){
            return;
        }else{
            for(int i=0; i<currentCup.size()-2; i++){
                List<Cup<String, Integer, Double>> newCupList = new ArrayList<>();
                for(int j=i+1; j<currentCup.size(); j++){
                    Double expSupMin = math.expSupXY(currentCup.get(i).getTEPList(),
                                                currentCup.get(j).getTEPList());
                    //combine two of cup
                    Cup<String, Integer, Double> cupXY = combineCup(currentCup.get(i), currentCup.get(j));
                    if (expSupMin > threshold) {
                    /*insert new UFP into top-k and set new threshold,
                    then remove the last UFP when expSup < threshold.*/  
                        topKUFP.add(new topK<String,Double>(cupXY.getNamePattern(), cupXY.getExpSupOfPattern()));
                        //use Comparator to sort topKUFP
                        Collections.sort(topKUFP, new Comparator<topK<String,Double>>() {
                            @Override
                            public int compare(topK<String,Double> t1, topK<String,Double> t2) {
                                // Sort topKUFP
                                return Double.compare(t2.getExpSupOfPattern(), t1.getExpSupOfPattern());
                            }
                        });
                        topKUFP.remove(topKUFP.size()-1); //remove last UFP
                        newCupList.add(cupXY);
                    }else{
                        newCupList.add(cupXY);
                    }
                }

                TUFPSearch(newCupList); //next Cup
            }
        }
    }

     public void TUFP(String filePath, int k){ 
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
        return "top UFPs:\n" + topKUFP;
    }
    
}