package main;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

import main.interfaces.AlgorithmTUFPInterface;
import main.objects.*;
import main.resource.MemoryLogger;

/**
 * This is an implementation of the TUFP algorithm.
 * The TUFP algorithm finds top k uncertain frequent patterns and their expected support.
 * @param <T1> type of name's UFPs
 * @param <T2> type of TID in TEP list of CUP
 * @param <T3> type of Expected Support, item's probability, threshold
 */
public class AlgorithmTUFP<T1, T2 extends Number & Comparable<T2>, T3 extends Number & Comparable <T3>> implements AlgorithmTUFPInterface<T1, T2, T3> {

    /** start time of latest execution */
    long startTimestamp = 0;

    /** end time of latest execution */
    long endTimeStamp = 0;

    /** the number of transactions */
    private int databaseSize = 0;

    /** the number of candidates found */
    private int candidates = 0;

    /**the result top-k UFP in the end*/
    public List<UFP<T1, T3>> topUFP;

    /**contain the single cup*/
    public Map<T1, Cup<T1, T2, T3>> singleCup;

    /**contain the list of CUP to make top k */
    public List<Cup<T1, T2, T3>> cupl;

    /**threshold is the condition to get into top-k UFP */
    public T3 threshold = (T3) Double.valueOf(0.0);

    /**
     * read the input file a uncertain dataset (transactions)
     * @param filePath path to dataset file
     * @param k number of top-k UFP
     */
    @Override
    public void readData(String filePath, int k) {
        // use try-with-resource to read file with BufferedReader
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            System.out.println("Reading data . . .");

            singleCup = new HashMap<>();
            cupl = new ArrayList<>();

            //read first line to get items from data
            String[] itemName = reader.readLine().split(",\\s|\\s|\\t|,");
            String line;
            Integer TID = 1; // transaction ID in a TEP list
            //read each line to get Prob in transaction of item
            while ((line = reader.readLine())!= null) {
                //split was separated by ','; space; tab or ', ' to array
                String[] probList = line.split(",\\s|\\s|\\t|,");
                for (int i=0; i<probList.length; i++){
//                  check prob equal 0 is true or not, if not add to item's list and probability's list
                    if (!probList[i].equals("0")){
                        T3 probability = (T3) Double.valueOf(probList[i]);
                        boolean cupExists = false;
                        for (Cup<T1, T2, T3> cup : cupl) {
                            if (cup.getNamePattern().equals(itemName[i])) {
                                // Update the existing Cup with the new probability
                                double expSup = cup.getExpSupOfPattern().doubleValue() + probability.doubleValue();
                                cup.setExpSupOfPattern((T3) Double.valueOf((Math.round(expSup*100.0)/100.0)));
                                cup.getTEPList().add(new Tep<>((T2) TID,probability));
                                if (cup.getMax().doubleValue()<probability.doubleValue()) {
                                    cup.setMax(probability);
                                }
                                singleCup.replace(cup.getNamePattern(), cup);
                                cupExists = true;
                                break;
                            }
                        }
                        // If a Cup doesn't exist, create a new one and add it to the CUP list
                        if (!cupExists) {
                            List<Tep<T2, T3>> tepList = new ArrayList<>();
                            tepList.add(new Tep<>((T2) TID, probability));
                            Cup<T1, T2, T3> cup = new Cup<>( (T1) itemName[i], probability, tepList, probability);
                            cupl.add(cup);
                            singleCup.put(cup.getNamePattern(), cup);
                        }

                        // Sort top UFPs
                        cupl.sort((t1, t2) -> {
                            return Double.compare(t2.getExpSupOfPattern().doubleValue(), t1.getExpSupOfPattern().doubleValue());
                        });
                    }
                }
                TID ++; // increment TID
                //increment databaseSize (number of transaction)
                databaseSize++;
            }
            // get first top-k from single CUP
            getFirstTop(k);
        } catch (IOException e) {
            //stop the algorithm if file not found
            System.out.println("File not found: " + e.getMessage());
            System.out.println("STOP ALGORITHM !!!");
            System.exit(0);
        }
    }

    /**
     * method to get first top-k
     * @param k elements in top
     */
    public void getFirstTop(int k){
        for(Cup<T1, T2, T3> cup:cupl){
            if (k>0) {
                topUFP.add(new UFP<>(cup.getNamePattern(), cup.getExpSupOfPattern()));
                k--;
            }else {
                break;
            }
        }
    }


    /**
     * remove duplicate character in a string
     * @param input a duplicate string
     * @return a non duplicate string
     */
    public T1 removeDuplicates(String input) {
        // System.out.println(input);
        String[] elements = input.split(",\\s*");
        //store unique element into set
        Set<String> uniqueElements = new LinkedHashSet<>(Arrays.asList(elements));

        // make name from these unique elements
        StringBuilder result = new StringBuilder();
        // for each element
        for (String uniqueElement : uniqueElements) {
            // check result has element or not
            if (result.length() > 0) {
                // elements are separated by a comma
                result.append(", ");
            }
            result.append(uniqueElement);
        }
        return (T1) String.valueOf(result);
    }

    /**
     * combined two TEP list to make a new TEP list
     * @param tepListX TEP list of a CUP X
     * @param tepListY TEP list of a CUP Y
     * @return a combined TEP list
     */
    public List<Tep<T2, T3>> combineTep(List<Tep<T2, T3>> tepListX, List<Tep<T2, T3>> tepListY){

        // new TEP list
        List<Tep<T2, T3>> tepListXY = new ArrayList<>();
        int i = 0;
        int j = 0;

        // for each TID in two TEP list
        while(i < tepListX.size() && j < tepListY.size()){
            Tep<T2, T3> tX = tepListX.get(i);
            Tep<T2, T3> tY = tepListY.get(j);

            // check TID in two TEP list
            // if TID in tepListX smaller than TID in tepListY
            if(tX.getTID().intValue() < tY.getTID().intValue()){
                // move next TID in tepListX
                i++;
            }
            // if TID in tepListX greater than TID in tepListY
            else if(tX.getTID().intValue() > tY.getTID().intValue()){
                // move next TID in tepListY
                j++;
            }
            // if two TID are equal
            else{
                // combine two TID
                Double temp = Double.valueOf(Math.round((tX.getExistentialProbability().doubleValue()*tY.getExistentialProbability().doubleValue())*100.0)/100.0);
                T3 tepXY = (T3) temp;
                // add new tep in to new TEP list
                tepListXY.add(new Tep<T2, T3>(tX.getTID(), tepXY));
                //move next TID
                i++;
                j++;
            }
        }
        return tepListXY;
    }

    /**
     * method to combine two of cup XY
     * @param cupX cup of item X
     * @param cupY cup of item Y
     * @return a combined CUP
     */
    public Cup<T1, T2, T3> combineCup(Cup<T1, T2, T3> cupX, Cup<T1, T2, T3> cupY) {

        //combine name's CupX with name's Cup Y --> name of Cup XY
        T1 nameXY = removeDuplicates( cupX.getNamePattern()+", "+cupY.getNamePattern());
        String[] countedTwice = nameXY.toString().split(", ");
        //combine tep list
        List<Tep<T2, T3>> tepListXY = combineTep(cupX.getTEPList(), singleCup.get(countedTwice[countedTwice.length-1]).getTEPList());

        //max of Prob in TEP of Cup XY
        T3 maxXY = (T3) Double.valueOf(0.0);

        //cal expSup of cupXY
        double sum = 0.0;
        for (Tep<T2, T3> tep:tepListXY){
            sum = Math.round((sum+tep.getExistentialProbability().doubleValue())*100.0)/100.0;
            //get max of Prob in tep list XY
            if(tep.getExistentialProbability().doubleValue() > maxXY.doubleValue()){
                maxXY = tep.getExistentialProbability();
            }
        }
        T3 expSup = (T3) Double.valueOf(sum);
        //return the new cup after combined
        return new Cup<T1,T2,T3>(nameXY, expSup, tepListXY, maxXY);

    }

    /**
     * method create a new UFP and add it into the result
     * @param combined a combined CUP
     * @param k number of top-k UFPs
     */
    private void TUFPSearchHelperMethod(Cup<T1, T2, T3> combined, int k) {
        //insert new UFP into top-k
        topUFP.add(new UFP<T1, T3>(combined.getNamePattern(), combined.getExpSupOfPattern()));
        //use Comparable to sort topUFP
        topUFP.sort((t1, t2) -> {
            // Sort topUFP
            return Double.compare(t2.getExpSupOfPattern().doubleValue(), t1.getExpSupOfPattern().doubleValue());
        });
        //if size of top-k UFP > k
        if (topUFP.size() > k) {
            topUFP.remove(topUFP.size() - 1); //remove last UFP
            //set new threshold
            threshold = topUFP.get(topUFP.size() - 1).getExpSupOfPattern();
        }
    }


    /**
     * this method use to search top UFPs in TUFP algorithm
     * @param currentCup list of the CUPs
     * @param k number of top-k UFPs
     */
    @Override
    public void TUFPSearch(List<Cup<T1, T2, T3>> currentCup, int k){
        //if size of current cup list <= 1, stop searching
        if(currentCup.size()<=1) {
            return;
        }
        double overestimate = 0.0;
        // for each cup in current cup list begin at 1st cup -> size - 1 cup
        for(int i=0; i<currentCup.size()-1; i++){
            //a new cup list will use to the next search
            List<Cup<T1, T2, T3>> newCupList = new ArrayList<>();
            // for each cup in current cup list begin at the 2nd cup -> end
            for(int j=i+1; j<currentCup.size(); j++){
                // calculate overestimate
                overestimate = currentCup.get(i).getExpSupOfPattern().doubleValue() * currentCup.get(j).getMax().doubleValue();
                //if overestimate < threshold, then stop searching.
                if (overestimate < threshold.doubleValue()) {
                    break;
                }
                //combine two of cup X and cup Y
                Cup<T1, T2, T3> combined = combineCup(currentCup.get(i), currentCup.get(j));
                candidates++; //increment the cup
                //if expSup of cupXY > threshold
                if (combined.getExpSupOfPattern().doubleValue() > threshold.doubleValue()) {
                    //insert new UFP into top-k
                    TUFPSearchHelperMethod(combined, k);
                    // add combined cup for next search
                    newCupList.add(combined);
                }
            }
            TUFPSearch(newCupList,k); //continue search
        }
    }

    /**
     * method to run TUFP algorithm
     * @param filePath path of database file
     * @param k number of top-k UFPs
     */
    @Override
    public void runTUFPAlgorithm(String filePath, int k) {

        // record start time
        startTimestamp = System.currentTimeMillis();

        // reset number of candidates found
        candidates = 0;

        //reset the number of transaction
        databaseSize = 0;

        topUFP = new LinkedList<>();

        //read input file
        readData(filePath, k);

        // create a temp cup list
        List<Cup<T1, T2, T3>> currentCup = new ArrayList<>();


        // handle the null cup
        if(cupl == null) {
            System.out.println("There isn't CUP List which was created. STOP ALGORITHM !!!");
            System.exit(0);
        }

        // add into currentCup with number element is k
        for(int i=0; i<cupl.size() && i<k; i++){
            currentCup.add(cupl.get(i));
        }

        // number of candidates
        candidates = currentCup.size();

        //set threshold
        threshold = topUFP.get(topUFP.size() - 1).getExpSupOfPattern();

        // check memory usage
        MemoryLogger.getInstance().checkMemory();

        //start searching
        TUFPSearch(currentCup,k);

        // save the end time
        endTimeStamp = System.currentTimeMillis();
    }

    /**
     * Print statistics about the algorithm execution
     * @param path of the output file
     */
    public void printStats(String path) {
        // Use try-with-resources to automatically close the PrintWriter
        try (PrintWriter outputWriter = new PrintWriter(path)) {
            for (UFP<T1, T3> t : topUFP) {
                // write top k to the file
                outputWriter.println(t);
            }
            //write statistics to the file
            outputWriter.println("=============  TOP-K UFPs v1.20 - STATS =============");
            outputWriter.println(" Transactions count from database : " + databaseSize);
            outputWriter.println(" Candidates count : " + candidates);
            outputWriter.println(" Maximum memory usage : " +
                MemoryLogger.getInstance().getMaxMemory() + " mb");
            outputWriter.println(" Total time ~ " + (endTimeStamp - startTimestamp)
                + " ms");
            outputWriter.println("===================================================");
        } catch (IOException e) {
            // Handle the exception
            e.printStackTrace();
        }
        // Print the statistics to the console
        System.out.println("=============  TOP-K UFPs v1.20 - STATS =============");
        System.out.println(" Transactions count from database : " + databaseSize);
        System.out.println(" Candidates count : " + candidates);
        System.out.println(" Maximum memory usage : " +
                MemoryLogger.getInstance().getMaxMemory() + " mb");
        System.out.println(" Total time ~ " + (endTimeStamp - startTimestamp)
                + " ms");
        System.out
                .println("===================================================");
    }
}
