package main;

import java.io.*;
import java.util.*;

import main.interfaces.AlgorithmTUHUFPInterface;
import main.objects.*;
import main.resource.MemoryLogger;

/**
 * This is an implementation of the TUHUFP algorithm.
 * The TUHUFP algorithm use to mining top k uncertain high utility frequent patterns
 * @param <T1> type of name's UHUFPs
 * @param <T2> type of TID in TEP list of CUP and the utility's value
 * @param <T3> type of Expected Support, item's probability, threshold
 */
public class AlgorithmTUHUFP<T1, T2 extends Number & Comparable<T2>, T3 extends Number & Comparable <T3>> implements AlgorithmTUHUFPInterface<T1, T2, T3> {

    /** start time of latest execution */
    long startTimestamp = 0;

    /** end time of latest execution */
    long endTimeStamp = 0;

    /** the number of transactions */
    private int databaseSize = 0;

    /** the utility of database */
    private int databaseUtil = 0;

    /** the number of candidates found */
    private int candidates = 0;

    /** the result top-k UHUFP in the end */
    public List<UHUFP<T1, T2, T3>> topUFP;

    /** contain the single cup */
    public Map<T1, Cup<T1, T2, T3>> singleCup;

    /** contain the candidates to make top k */
    public List<Cup<T1, T2, T3>> cupl;

    /** threshold is the condition to get into top-k UHUFP */
    public T3 threshold =  (T3) Double.valueOf(Double.MIN_VALUE);

    /** user-specified minimum utility threshold */
    public T2 minUtil = (T2) Integer.valueOf(Integer.MIN_VALUE);

    /**
     * read the input file an uncertain dataset (transactions)
     * @param filePath path to dataset file
     * @param percentage to set min utility to find high utility
     * @param k number of top-k UHUFP
     */
    @Override
    public void readData(String filePath, double percentage, int k) {
        String[] path = filePath.split(", ");
        // use try-with-resource to read file with BufferedReader
        try (BufferedReader reader1 = new BufferedReader(new FileReader(path[0]));
            BufferedReader reader2 = new BufferedReader(new FileReader(path[1]))) {
            System.out.println("Reading data . . .");

            singleCup = new HashMap<>();
            cupl = new ArrayList<>();
            //read first line to get items from data
            String[] itemName = reader1.readLine().split(",\\s|\\s|\\t|,");
            String probLine; // contain probability in line
            String utilLine; // contain utility in line
            Integer TID = 1; // transaction ID in a TEP list

            //read each line to get Prob, Util in transaction of item
            while ((probLine = reader1.readLine())!= null && (utilLine = reader2.readLine()) != null) {
                //process data
                processData(itemName, probLine, utilLine, TID);
                TID ++; // increment TID
                //increment databaseSize (number of transaction)
                databaseSize ++;
            }
            minUtil = (T2) Double.valueOf(databaseUtil*percentage);
        } catch (IOException e) {
            //stop the algorithm if file not found
            System.out.println("File not found: " + e.getMessage());
            System.out.println("STOP ALGORITHM !!!");
            System.exit(0);
        }
    }

    /**
     * Process data to create candidates
     * @param itemName list of item's name
     * @param probLine probability of items in a transaction
     * @param utilLine utility of items in a transaction
     * @param TID transaction identify
     */
    public void processData(String[] itemName, String probLine, String utilLine, Integer TID){
        //split was separated by ','; space; tab or ', ' to array
        String[] probList = probLine.split(",\\s|\\s|\\t|,");
        String[] trans = utilLine.split(":");
        T2 transUtil = (T2) Integer.valueOf(trans[1]);
        String[] utilList = trans[2].split(" ");

        int j = 0;
        for (int i=0; i<probList.length; i++){
            // check prob equal 0 is true or not, if not add to item's list and probability's list
            if (!probList[i].equals("0")){
                T3 probability = (T3) Double.valueOf(probList[i]);
                T2 utilValue = (T2) Integer.valueOf(utilList[j]);
                j++;
                boolean cupExists = false;
                for (Cup<T1, T2, T3> cup : cupl) {
                    if (cup.getNamePattern().equals(itemName[i])) {
                        // Update the existing Cup with the new probability and new utility
                        updateCupList( cup, probability, (T2) TID, transUtil, utilValue);
                        // set status cup to existing
                        cupExists = true;
                        break;
                    }
                }
                // If a Cup doesn't exist, create a new one and add it to the CUP list
                if (!cupExists) {
                    createNewCup((T1) itemName[i], probability, (T2) TID, transUtil, utilValue);
                }
                // Sort top UHUFPs
                cupl.sort((t1, t2) ->
                        Double.compare(t2.getExpSupOfPattern().doubleValue(), t1.getExpSupOfPattern().doubleValue()));
            }
        }
        // increment utility of database
        databaseUtil += transUtil.intValue();
    }

    /**
     * method to update the cup list when have the item existed in cup list
     * @param cup existed cup
     * @param probability probability of item
     * @param TID transaction identify of transaction where item located
     * @param transUtil transaction's utility where item located
     * @param utilValue item's utility
     */
    public void updateCupList(Cup<T1, T2, T3> cup, T3 probability, T2 TID, T2 transUtil, T2 utilValue){
        double expSup = cup.getExpSupOfPattern().doubleValue() + probability.doubleValue();
        int utility = cup.getUtility().intValue() + utilValue.intValue();
        cup.setExpSupOfPattern((T3) Double.valueOf((Math.round(expSup*100.0)/100.0)));
        cup.setUtility((T2) Integer.valueOf(utility));
        cup.getTEPList().add(new Tep<>(TID,probability, utilValue, transUtil));
        if (cup.getMax().doubleValue()<probability.doubleValue()) {
            cup.setMax(probability);
        }
        int TWU = cup.getTransWeiUtil().intValue() + transUtil.intValue();
        cup.setTransWeiUtil((T2) Integer.valueOf(TWU));
        singleCup.replace(cup.getNamePattern(), cup);
    }

    /**
     * method to create the new cup when item do not exist in cup list
     * @param name of item
     * @param probability probability of item
     * @param TID transaction identify of transaction where item located
     * @param transUtil transaction's utility where item located
     * @param utilValue item's utility
     */
    public void createNewCup(T1 name, T3 probability, T2 TID, T2 transUtil, T2 utilValue){
        List<Tep<T2, T3>> tepList = new ArrayList<>();
        tepList.add(new Tep<>((T2) TID, probability, utilValue, transUtil));
        Cup<T1, T2, T3> cup = new Cup<>(name, probability, tepList, probability, transUtil, utilValue);
        cupl.add(cup);
        singleCup.put(cup.getNamePattern(), cup);
    }

    /**
     * method to get first top-k
     * @param k number elements in top
     */
    public List<Cup<T1, T2, T3>> getFirstHUFP(T2 minUtil, int k){
        topUFP = new ArrayList<>();
        List<Cup<T1, T2, T3>> candidateList = new ArrayList<>();
        for(Cup<T1, T2, T3> cup:cupl){
//            System.out.println(cup.getNamePattern() + "====="+ cup.getUtility());
            if (k <= 0) {
                break;
            }
            // check the supersets of item may is high utility, then add into candidate high utility list
            if (cup.getTransWeiUtil().intValue() >= minUtil.intValue()){
                candidateList.add(cup);
            }
            // add to the result
            if (cup.getUtility().intValue() >= minUtil.intValue()){
                topUFP.add(new UHUFP<>(cup.getNamePattern(), cup.getExpSupOfPattern(), cup.getUtility()));
                topUFP.sort((t1, t2) ->
                        Double.compare(t2.getExpSupOfPattern().doubleValue(), t1.getExpSupOfPattern().doubleValue()));
            }
            k--;
        }
        return candidateList;
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
            if (!result.isEmpty()) {
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
            int compareResult = tX.getTID().compareTo(tY.getTID());
            // if TID in tepListX smaller than TID in tepListY
            if(compareResult < 0){
                // move next TID in tepListX
                i++;
            }
            // if TID in tepListX greater than TID in tepListY
            else if(compareResult > 0){
                // move next TID in tepListY
                j++;
            }
            // if two TID are equal
            else{
                // combine two TID
                double temp = Math.round((tX.getExistentialProbability().doubleValue()*tY.getExistentialProbability().doubleValue())*100.0)/100.0;
                T3 probXY = (T3) Double.valueOf(temp);
                T2 utilXY = (T2) Integer.valueOf(tX.getUtility().intValue() + tY.getUtility().intValue());
                // add new tep in to new TEP list
                tepListXY.add(new Tep<>(tX.getTID(), probXY, utilXY, tX.getTransUtil()));
                // move next TID
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
        double sumProb = 0.0;
        //transaction weighted utility of Cup XY
        int twuXY = 0;
        //cal utility of XY
        int sumUtil = 0;

        for (Tep<T2, T3> tep:tepListXY){
            T3 tepProb = tep.getExistentialProbability();
            sumProb = Math.round((sumProb + tepProb.doubleValue())*100.0)/100.0;
            sumUtil += tep.getUtility().intValue();
            twuXY += tep.getTransUtil().intValue();
            //get max of Prob in tep list XY
            if(tepProb.doubleValue() > maxXY.doubleValue()){
                maxXY = tepProb;
            }
        }
        T3 expSup = (T3) Double.valueOf(sumProb);
        T2 utility = (T2) Integer.valueOf(sumUtil);
        //return the new cup after combined
        return new Cup<>(nameXY, expSup, tepListXY, maxXY, (T2) Integer.valueOf(twuXY), utility);

    }

    /**
     * method to check and create a new UHUFP, then add it into the result
     * @param combined a combined CUP
     * @param k number of top-k UHUFPs
     */
    private void TUHUFPSearchHelper(Cup<T1, T2, T3> combined, int k) {
        //check if it is high utility or not
        if (combined.getUtility().intValue() >= minUtil.intValue()){
            //insert new UHUFP into top-k
            topUFP.add(new UHUFP<>(combined.getNamePattern(), combined.getExpSupOfPattern(), combined.getUtility()));
            //use Comparable to sort topUHUFP
            topUFP.sort((t1, t2) -> {
                // Sort topUFP
                return Double.compare(t2.getExpSupOfPattern().doubleValue(), t1.getExpSupOfPattern().doubleValue());
            });
        }
        //if size of top-k UHUFP > k
        if (topUFP.size() > k) {
            topUFP.remove(topUFP.size() - 1); //remove last UFP
        }
        if (topUFP.size()==k){
            //set new threshold
            threshold = topUFP.get(topUFP.size() - 1).getExpSupOfPattern();
        }
    }

    /**
     * this method use to search top UHUFPs in TUHUFP algorithm
     * @param currentCup list of the CUPs
     * @param k number elements in top-k UHUFPs
     */
    @Override
    public void TUHUFPSearch(List<Cup<T1, T2, T3>> currentCup, int k){
        //if size of current cup list <= 1, stop searching
        if(currentCup.size()<=1) {
            return;
        }
        // for each cup in current cup list begin at 1st cup -> size - 1 cup
        for(int i=0; i<currentCup.size()-1; i++){
            //a new cup list wi ll use to the next search
            List<Cup<T1, T2, T3>> newCupList = new ArrayList<>();
            // for each cup in current cup list begin at the 2nd cup -> end
            for(int j=i+1; j<currentCup.size(); j++){
                // calculate overestimate
                double overestimate = currentCup.get(i).getExpSupOfPattern().doubleValue() * currentCup.get(j).getMax().doubleValue();
                //if overestimate < threshold, then stop searching.
                if (overestimate < threshold.doubleValue()) {
                    break;
                }
                //combine two of cup X and cup Y
                Cup<T1, T2, T3> combined = combineCup(currentCup.get(i), currentCup.get(j));

//                System.out.println(combined.getNamePattern()+ " : "+ combined.getUtility() +" : " + minUtil
//                        + " : "+ combined.getExpSupOfPattern()+ " : "+ threshold);

                //if expSup of cupXY > threshold
                if (combined.getExpSupOfPattern().doubleValue() > threshold.doubleValue()) {
                    //insert new UHUFP into top-k
                    TUHUFPSearchHelper(combined, k);
                    // check the subsets of combined, can be high utility or not
                    if (combined.getTransWeiUtil().intValue() >= minUtil.intValue()){
                        // add combined cup for next search
                        newCupList.add(combined);
                        candidates++; //increment the candidate
                    }
                }
            }
            TUHUFPSearch(newCupList,k); //continue search
        }
    }

    /**
     * method to run TUHUFP algorithm
     * @param filePath path of database file
     * @param k number elements of top-k UHUFPs
     */
    @Override
    public void runTUHUFPAlgorithm(String filePath, double percentage, int k) {

        // record start time
        startTimestamp = System.currentTimeMillis();

        // reset memory usage
        MemoryLogger.getInstance().reset();

        // reset number of candidates found
        candidates = 0;

        //reset the number of transaction
        databaseSize = 0;

        //read input file
        readData(filePath, percentage, k);

        // candidate's list engage to find the result
        List<Cup<T1, T2, T3>> candidateList = getFirstHUFP(minUtil, k);

        // handle the null cup
        if(cupl == null) {
            System.out.println("There isn't CUP List which was created. STOP ALGORITHM !!!");
            System.exit(0);
        }

        // number of candidates
        candidates = candidateList.size();

        //set threshold
        if (!topUFP.isEmpty()){
            threshold = topUFP.get(topUFP.size() - 1).getExpSupOfPattern();
        }

        //start searching
        TUHUFPSearch(candidateList,k);
        // check memory usage
        MemoryLogger.getInstance().checkMemory();

        // save the end time
        endTimeStamp = System.currentTimeMillis();
    }

    /**
     * helper to print statistic
     * @param outputWriter path of output file
     */
    public void writeHelper(PrintWriter outputWriter){
        outputWriter.println("=============  TOP-K UFPs v1.20 - STATS =============");
        outputWriter.println(" Transactions count from database : " + databaseSize);
        outputWriter.println(" Candidates count : " + candidates);
        outputWriter.println(" Maximum memory usage : " +
                MemoryLogger.getInstance().getMaxMemory() + " mb");
        outputWriter.println(" Total time ~ " + (endTimeStamp - startTimestamp)
                + " ms");
        outputWriter.println("===================================================");
    }

    /**
     * Print statistics about the algorithm execution
     * @param path of the output file
     */
    public void printStats(String path) {
        // Use try-with-resources to automatically close the PrintWriter
        try (PrintWriter outputWriter = new PrintWriter(path)) {
            outputWriter.println("minUtil: " + minUtil);

            for (UHUFP<T1, T2, T3> t : topUFP) {
                // write top k to the file
                outputWriter.println(t);
            }
            //write statistics to the file
            writeHelper(outputWriter);

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
