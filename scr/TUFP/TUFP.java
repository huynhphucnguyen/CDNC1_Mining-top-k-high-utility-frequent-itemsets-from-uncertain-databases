package TUFP;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

import ca.pfv.spmf.tools.MemoryLogger;
import cupList.*;
import utility.*;

/**
 * This is an implementation of the TUFP algorithm.<br/><br/>
 * 
 * The TUFP algorithm finds top k uncertain frequent patterns and their expected
support
 *@see topK
 *@see Cup
 *@see mathFormulas
 */
public class TUFP{
    /** start time of latest execution */
	long startTimestamp = 0; 
	
	/** end time of latest execution */
	long endTimeStamp = 0;

    /** the number of transactions */
	private int databaseSize = 0;

    /** the number of frequent itemsets found */
	private int itemsetCount = 0;

    /**the result top-k UFP in the end*/
    public List<topK<String, Double>> topKUFP;

    /** include the sigle cup */
    public List<Cup<String, Integer, Double>> cupl;

    /**threshold is the condition to get into top-k UFP */
    public Double threshold = 0.0;

    /**the utility to calculate expSup or Prob in TEP */
    public mathFormulas<Integer, Double> math = new mathFormulas<>();
    
    /**
     * read the input file: a uncertain dataset (trasactions)
     * @param filePath
     */
    public void readData(String filePath) {
        try {
            //Read the input file
            FileReader file = new FileReader(filePath);
            BufferedReader reader  = new BufferedReader(file);

            //list included Prob of each transaction
            List<List<Double>> existensialProbability = new ArrayList<>();

            //item's name in 1st line
            String[] itemName = reader.readLine().split(",\\s|\\s|\\t|,");

            // Convert String[ ] itemName to a List<String>
            List<String> itemList = new ArrayList<>(); //This is list of item's name
            for (String item : itemName) {
                itemList.add(item);
            }

            // System.out.println(itemList.size());

            String line;
            //read each line to get Prob in transaction of item
            while ((line = reader.readLine())!= null) { 
                
                //split was separated by ','; space; tab or ', ' to array
                String[] values = line.split(",\\s|\\s|\\t|,");

                //Prob of item
                List<Double> eps = new ArrayList<>();

                //get Prob at item in the index i (can change i=0 or i=1 if data have TID (1) or not (0))
                for (int i = 0; i < values.length; i++) {
                    String valueStr = values[i];
                    Double ep = null;
                    //convert String to Double
                    ep = Double.parseDouble(valueStr);
                    //add into a list Prob
                    eps.add(ep);
                }

                //add a eps list into a new list
                existensialProbability.add(eps);
                //increment datasize (number of transaction)
                databaseSize++;
            }

            //create sigle cups
            cupList<String, Integer, Double> cupTempt = new cupList<>();
            
            /**
             * use method @see createCupList to make sigle cup
             */
            cupl = cupTempt.createCupList(itemList, existensialProbability); // cuplist

            //size of first cup list
            itemsetCount = cupl.size();
            // System.out.println(itemsetCount);
            reader.close(); //close the input file
            
        } catch (IOException e) {
            System.out.println("File not found: " + e.getMessage());
        }
    }
   
    /** 
     * method to combine two of cup XY
     * @param cupX cup of item X
     * @param cupY cup of item Y
     * @return a cup was combined by cup X and cup Y
     */
    public Cup<String, Integer, Double> combineCup(Cup<String, Integer, Double> cupX, Cup<String, Integer, Double> cupY){
        List<Tep<Integer, Double>> tepListXY = new ArrayList<>();
        
        //combine name Cup X with name Cup Y --> name of Cup XY
        StringBuilder combined = new StringBuilder(cupX.getNamePattern());
        combined.append(", "); //seperate by ", "
        combined.append(cupY.getNamePattern());
        String nameXY = String.valueOf(combined); //convert StringBuilder to String
        
        //cal expSup of cupXY by method expSupXY in mathFormulas
        Double expSup = math.expSupXY(cupX.getTEPList(),cupY.getTEPList());

        //max of Prob in TEP of Cup XY
        Double maxXY = 0.0;
        
        //for each tep in tep list of cup X
        int i = 0;
        for(Tep<Integer, Double> tX: cupX.getTEPList()){

            //if i < size of tep list of cup Y, else stop this loop
            if (i<cupY.getTEPList().size()) {
                //tep of tep list (cup Y) at index i
                Tep<Integer, Double> tY = cupY.getTEPList().get(i);
                
                //if TID of tep X < TID of tep Y
                if(tX.getTID().compareTo(tY.getTID())<0){
                    continue; //go to the next TID of tep X
                }
                //if TID of tep X > TID of tep Y
                else if(tX.getTID().compareTo(tY.getTID())>0){
                    i++; //increment index of TID in tep Y
                }
                    //if same TID, then add TID and tepXY into tep list XY
                else{
                    //cal tepXY by ProbX*ProbY
                    Double tepXY = (Math.round(tX.getExistensialProbability() * tY.getExistensialProbability())*100.0)/100.0;
                    //get max of Prob in tep list XY
                    if(tepXY > maxXY){
                        maxXY = tepXY;
                    }
                    //add new tepXY into tep list XY
                    tepListXY.add(new Tep<Integer, Double>(tX.getTID(), tepXY));
                    i++;
                }
            }else{
                break;
            }
        }
        //return the new cup after combined
        return new Cup<String,Integer,Double>(nameXY, expSup, tepListXY, maxXY);
    }
    

    /**
     * method use call combineCup(...) to combine these cup then search the top UFP
     * @param currentCup list of sigle or multi cup after combined
     * @param k the number element in top UFP
     */
    
    public void TUFPSearch(List<Cup<String, Integer, Double>> currentCup, int k){
        // for(Cup<String, Integer, Double> c:currentCup){
        //     System.out.println(c);
        // }
        // System.out.println("--------------------------------------");
        
        //if size of current cup list > 1
        if(currentCup.size()>1){

            //cal the overestimate expSup of two firt cup: overestimate = expSup(X)*Max(Y)
            Double overestimate = currentCup.get(0).getExpSupOfPattern()*currentCup.get(1).getMax();
            
            //if overestimate < threshold, then stop searching.
            if(overestimate < threshold){
                return;
            }else{
                //for cup[0] to cup[n-1] of current cup 
                for(int i=0; i<currentCup.size()-1; i++){

                    //a new cup list will use to the next search
                    List<Cup<String, Integer, Double>> newCupList = new ArrayList<>();

                    //for cup[1] to cup[n] of current cup
                    for(int j=i+1; j<currentCup.size(); j++){
                        
                        //combine two of cup X and cup Y
                        Cup<String, Integer, Double> cupXY = combineCup(currentCup.get(i), currentCup.get(j));
                        
                        itemsetCount++; //increment the cup
                        
                        //if expSup of cupXY > threshold
                        if (cupXY.getExpSupOfPattern() > threshold) {
                        //insert new UFP into top-k
                            topKUFP.add(new topK<String,Double>(cupXY.getNamePattern(), cupXY.getExpSupOfPattern()));
                            //use Comparator to sort topKUFP
                            Collections.sort(topKUFP, new Comparator<topK<String,Double>>() {
                                @Override
                                public int compare(topK<String,Double> t1, topK<String,Double> t2) {
                                    // Sort topKUFP
                                    return Double.compare(t2.getExpSupOfPattern(), t1.getExpSupOfPattern());
                                }
                            });

                            //if size of top-k UFP > k 
                            if(topKUFP.size()>k){
                                topKUFP.remove(topKUFP.size()-1); //remove last UFP
                                //set new threshold
                                threshold = topKUFP.get(topKUFP.size()-1).getExpSupOfPattern(); 
                            }
                            newCupList.add(cupXY); //add into new cup list
                        }else{
                            newCupList.add(cupXY);//add into new cup list
                        }
                    }

                    TUFPSearch(newCupList,k); //continute search
                }
            }
        }else {
            return;
        }
    }

    /**
     * method to run TUFP algorithm on the transaction are given
     * @param filePath path of database file
     * @param k the number of UFP in top-k
     */
    public void TUFP(String filePath, int k){
        // record start time
        startTimestamp = System.currentTimeMillis();

        // reset number of itemsets found
		itemsetCount = 0;

        //reset the number of transaction
        databaseSize = 0;

        //read input file
        readData(filePath);

        topKUFP = new ArrayList<>();
        List<Cup<String, Integer, Double>> currentCup = new ArrayList<>(); 
        
        // check memory usage
        MemoryLogger.getInstance().checkMemory();

        //for each cup
        for(int i=0; i<cupl.size(); i++){
            /*if i < k that means the number of the index in the cup list is still smaller 
            the number of UFPs which needed, if not then stop loop*/
            if(i<k){
                //create top-k UFP
                topKUFP.add(new topK<String,Double>(cupl.get(i).getNamePattern(),cupl.get(i).getExpSupOfPattern()));
                //create the current cup for seaching
                currentCup.add(cupl.get(i));
            }else{
                break;
            }
        }
        
        //set threshold
        threshold = topKUFP.get(topKUFP.size()-1).getExpSupOfPattern();

        //start searching
        TUFPSearch(currentCup,k);

        // save the end time
		endTimeStamp = System.currentTimeMillis();
    }


    /**
	 * Print statistics about the algorithm execution to System.out.
	 */
	public void printStats() {
		System.out.println("=============  TOP-K UFPS v1 - STATS =============");
		System.out.println(" Transactions count from database : " + databaseSize);
		System.out.println(" Frequent itemsets count : " + itemsetCount);
		System.out.println(" Maximum memory usage : " + 
				MemoryLogger.getInstance().getMaxMemory() + " mb");
		System.out.println(" Total time ~ " + (endTimeStamp - startTimestamp)
				+ " ms");
		System.out
				.println("===================================================");
	}



    @Override
    public String toString() {
        return "top UFPs:\n" + topKUFP;
    }
    
}