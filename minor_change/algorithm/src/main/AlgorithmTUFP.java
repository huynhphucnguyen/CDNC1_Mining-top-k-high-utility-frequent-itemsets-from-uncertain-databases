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
    public List<UFP<T1, T2>> topUFP;

    /** include the single cup */
    public Map<T1, Cup<T1, T2>> cupl;

    /** first top k HUI */
    public List<Cup<T1, T2>> singleCup;

    /** threshold is the condition to get into top-k UFP */
    public T2 threshold = (T2) Integer.valueOf(0);

    /**  user-specified minimum utility threshold */
    public T2 minUtil = (T2) Integer.valueOf(0);

    /**
     * read the input file a uncertain dataset (transactions)
     * @param filePath path to dataset file
     * @param percentage number of top-k UFP
     */
    @Override
    public void readData(String filePath, double percentage, int k) {
        // use try-with-resource to read file with BufferedReader
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            System.out.println("Reading data . . .");
            cupl = new HashMap<>();
            String line;
            int dbUtil = 0;
            Integer TID = 1; // transaction ID in a TEP list
            //read each line to get Prob in transaction of item
            while ((line = reader.readLine())!= null) {
                //split was separated by ','; space; tab or ', ' to array
                String[] trans = line.split(":");
                String[] item = trans[0].split(" ");
                T2 TU = (T2) Integer.valueOf(trans[1]);
                String[] itemUtil = trans[2].split(" ");

                for (int i=0; i<item.length; i++){
                    T2 util = (T2) Integer.valueOf(itemUtil[i]);
                    T1 name = (T1) item[i];
                    if (cupl.containsKey(name)){
                        Cup<T1, T2> cup = cupl.get(name);
                        int utility = cup.getUtil().intValue() + util.intValue();
                        cup.setUtil((T2) Integer.valueOf(utility));
                        cup.getTEPList().add(new Tep<>((T2) TID,util, TU));
                        int TWU = cup.getTransWeiUtil().intValue()+TU.intValue();
                        cup.setTransWeiUtil((T2) Integer.valueOf(TWU));
                    }else { // If a Cup doesn't exist, create a new one and add it to the CUP list
                        List<Tep<T2>> tepList = new ArrayList<>();
                        tepList.add(new Tep<>((T2) TID, util, TU));
                        Cup<T1, T2> cup = new Cup<>( name, util, tepList, TU);
                        cupl.put(cup.getNamePattern(), cup);
                    }
                }
                dbUtil += TU.intValue();
                TID ++; // increment TID
                //increment databaseSize (number of transaction)
                databaseSize++;
            }
            minUtil = (T2) Double.valueOf(dbUtil*percentage);
            System.out.println(minUtil);
            // get top UFPs
            getFirstHUI(minUtil, k);
        } catch (IOException e) {
            //stop the algorithm if file not found
            System.out.println("File not found: " + e.getMessage());
            System.out.println("STOP ALGORITHM !!!");
            System.exit(0);
        }
    }

    public void getFirstHUI(T2 minUtil, int k){
        singleCup = new ArrayList<>();
        topUFP = new ArrayList<>();
        for(Cup<T1, T2> cup:cupl.values()){
           if (cup.getTransWeiUtil().doubleValue() >= minUtil.doubleValue()){
               singleCup.add(cup);
               singleCup.sort((t1, t2) -> {
                   return Integer.compare(t2.getTransWeiUtil().intValue(), t1.getTransWeiUtil().intValue());
               });
           }
           if (cup.getUtil().doubleValue() >= minUtil.doubleValue()){
                topUFP.add(new UFP<>(cup.getNamePattern(), cup.getUtil()));
                topUFP.sort((t1, t2) -> {
                    return Integer.compare(t2.getUtil().intValue(), t1.getUtil().intValue());
                });
           }
        }
    }

    /**
     * create a list of single cup from the given transaction's list
     * @param transactions list of transactions
     * @param k number of top-k UFPs
     */
   /* @Override
    public void createFirstUFPs(List<Transaction<T1, T3>> transactions, int k){

    }*/

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
    public List<Tep<T2>> combineTep(List<Tep<T2>> tepListX, List<Tep<T2>> tepListY){

        // new TEP list
        List<Tep<T2>> tepListXY = new ArrayList<>();
        int i = 0;
        int j = 0;

        // for each TID in two TEP list
        while(i < tepListX.size() && j < tepListY.size()){
            Tep<T2> tX = tepListX.get(i);
            Tep<T2> tY = tepListY.get(j);

            // check TID in two TEP list
            // if TID in tepListX smaller than TID in tepListY
            if(tX.getTID().compareTo(tY.getTID()) < 0){
                // move next TID in tepListX
                i++;
            }
            // if TID in tepListX greater than TID in tepListY
            else if(tX.getTID().compareTo(tY.getTID())>0){
                // move next TID in tepListY
                j++;
            }
            // if two TID are equal
            else{
                // combine two TID
                int temp = tX.getUtility().intValue()+tY.getUtility().intValue();
                if (temp!=0){
                    T2 tepXY = (T2) Integer.valueOf(temp);
                    // add new tep in to new TEP list
                    tepListXY.add(new Tep<>(tX.getTID(), tepXY, tX.getTransUtility()));
                    //move next TID
                }
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
    public Cup<T1, T2> combineCup(Cup<T1, T2> cupX, Cup<T1, T2> cupY) {

        //combine name's CupX with name's Cup Y --> name of Cup XY
        T1 nameXY = removeDuplicates( cupX.getNamePattern()+", "+cupY.getNamePattern());

        String[] countedTwice = nameXY.toString().split(", ");
        //combine tep list
        List<Tep<T2>> tepListXY = combineTep(cupX.getTEPList(), cupY.getTEPList());

        //transaction weighted utility of Cup XY
        int twuXY = 0;

        //cal utility of XY
        int sum = 0;
        for (Tep<T2> tep:tepListXY){
            sum += tep.getUtility().intValue();
            twuXY += tep.getTransUtility().intValue();
        }
        T2 utility = (T2) Integer.valueOf(sum);
        //return the new cup after combined
        return new Cup<>(nameXY, utility, tepListXY, (T2) Integer.valueOf(twuXY));

    }

    /**
     * method create a new UFP and add it into the result
     * @param combined a combined CUP
     * @param k number of top-k UFPs
     */
    private void TUFPSearchHelperMethod(Cup<T1, T2> combined, int k) {
        //insert new UFP into top-k
        topUFP.add(new UFP<>(combined.getNamePattern(), combined.getUtil()));
        //use Comparable to sort topUFP
        topUFP.sort((t1, t2) -> {
            // Sort topUFP
            return Double.compare(t2.getUtil().doubleValue(), t1.getUtil().doubleValue());
        });
//        if size of top-k UFP > k
        if (topUFP.size() > k) {
            topUFP.remove(topUFP.size() - 1); //remove last UFP
            //set new threshold
            threshold = topUFP.get(topUFP.size() - 1).getUtil();
        }
    }

//    /**
//     *
//     * @param cupX cup of item X
//     * @param cupY cup of item Y
//     * @param k number of top-k UFPs
//     * @return a combined CUP
//     */
//    private Cup<T1, T2, T3> combineHelper(Cup<T1,T2,T3> cupX, Cup<T1,T2,T3> cupY, int k) {
//        Cup<T1, T2, T3> combined = combineCup(cupX, cupY); // combine cups
//        candidates++; // increment candidate engaged to make top UFPs
//        // check the condition before add to the result
//        if (combined.getExpSupOfPattern().doubleValue() > threshold.doubleValue()) {
//            TUFPSearchHelperMethod(combined,k);
//        }
//        return combined;
//    }
//
    /**
     * this method use to search top UFPs in TUFP algorithm
     * @param currentCup list of the CUPs
     * @param k number of top-k UFPs
     */
    @Override
    public void TUFPSearch(Cup<T1, T2> currentCup,int index, int k){
        // for each cup in current cup list begin at 1st cup -> size - 1 cup

        if (currentCup.getTransWeiUtil().doubleValue() < threshold.doubleValue()){
            return;
        }

        for(int i = index + 1; i<singleCup.size(); i++){
            Cup<T1, T2> combined = combineCup(currentCup,singleCup.get(i));

            if (combined.getUtil().doubleValue() > 0){
                System.out.println(combined.getNamePattern() +" "+ combined.getUtil());
                if (combined.getUtil().doubleValue() > threshold.doubleValue()){
                    TUFPSearchHelperMethod(combined, k);
                }
                TUFPSearch(combined,i,k);
            }
        }
    }

    /**
     * method to run TUFP algorithm
     * @param filePath path of database file
     * @param k number of top-k UFPs
     */
    @Override
    public void runTUFPAlgorithm(String filePath, double percentage, int k) {

        // record start time
        startTimestamp = System.currentTimeMillis();

        // reset number of candidates found
        candidates = 0;

        //reset the number of transaction
        databaseSize = 0;

        topUFP = new LinkedList<>();

        //read input file
        readData(filePath, percentage, k);


        // number of candidates
        candidates = singleCup.size();
        for (Cup<T1, T2> cup:singleCup){
            System.out.println(cup.getNamePattern() + " === " + cup.getTransWeiUtil());
        }

        //set threshold
//        if (!topUFP.isEmpty() && topUFP.size() == k-1){
//            threshold = topUFP.get(topUFP.size() - 1).getUtil();
//        }

        // check memory usage
        MemoryLogger.getInstance().checkMemory();

        //start searching
        int i = 0;
        for (Cup<T1, T2> cup:singleCup){
            TUFPSearch(cup,i,k);
            i++;
        }

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
            outputWriter.println("minUtil: " + minUtil);

            for (UFP<T1, T2> t : topUFP) {
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
