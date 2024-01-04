package main;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import main.interfaces.AlgorithmTUFPInterface;
import main.objects.*;
import main.resource.MemoryLogger;

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

    /** include the sigle cup */
    public List<Cup<T1, T2, T3>> cupl;

    /**threshold is the condition to get into top-k UFP */
    public T3 threshold = (T3) Double.valueOf(0.0);


    @Override
    public void readData(String filePath, int k) {

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {

            List<Transaction<T1, T3>> transactions = new ArrayList<>();

            String[] itemName = reader.readLine().split(",\\s|\\s|\\t|,");

            // System.out.println(itemList.size());

            String line;
            //read each line to get Prob in transaction of item
            while ((line = reader.readLine())!= null) {

                //split was separated by ','; space; tab or ', ' to array
                String[] probList = line.split(",\\s|\\s|\\t|,");

                List<T1> item = new ArrayList<>();
                List<T3> prob = new ArrayList<>();

                for (int i=1; i<probList.length; i++){
//                    System.out.println(probList[i]);
                    if (!probList[i].equals("0")){
                        item.add((T1) itemName[i-1]);
                        prob.add((T3)Double.valueOf(probList[i]));
                    }
                }

                //add a eps list into a new list
                transactions.add(new Transaction<>(item,prob));
                //increment databaseSize (number of transaction)
                databaseSize++;
            }

            createFirstUFPs(transactions, k);

        } catch (IOException e) {
            System.out.println("File not found: " + e.getMessage());
            System.out.println("STOP ALGORITHM !!!");
            System.exit(0);
        }

    }

    @Override
    public void createFirstUFPs(List<Transaction<T1, T3>> transactions, int k){

        cupl = new LinkedList<>();

        Integer TID = 1;

        for (Transaction<T1, T3> transaction:transactions){

            for (int i = 0; i<transaction.getItems().size(); i++) {
                T1 item = transaction.getItems().get(i);
                T3 probability = transaction.getProbabilities().get(i);

                // Check if a Cup for this item already exists in the result list
                boolean cupExists = false;
                for (Cup<T1, T2, T3> cup : cupl) {
                    if (cup.getNamePattern().equals(item)) {
                        // Update the existing Cup with the new probability
                        double expSup = cup.getExpSupOfPattern().doubleValue() + probability.doubleValue();
                        cup.setExpSupOfPattern((T3) Double.valueOf((Math.round(expSup*100.0)/100.0)));
                        cup.getTEPList().add(new Tep<T2, T3>((T2) TID,probability));

                        if (cup.getMax().doubleValue()<probability.doubleValue()) {
                            cup.setMax(probability);
                        }
                        cupExists = true;
                        break;
                    }
                }

                // If a Cup doesn't exist, create a new one and add it to the result list
                if (!cupExists) {
                    List<Tep<T2, T3>> tepList = new ArrayList<>();
                    tepList.add(new Tep<>((T2) TID, probability));
                    Cup<T1, T2, T3> cup = new Cup<>(item, probability, tepList, probability);
                    cupl.add(cup);
                }

                cupl.sort((t1, t2) -> {
                    // Sort topKUFP
                    return Double.compare(t2.getExpSupOfPattern().doubleValue(), t1.getExpSupOfPattern().doubleValue());
                });
            }
            TID++;

        }

        for(Cup<T1, T2, T3> cup:cupl){
            if (k>0) {
                topUFP.add(new UFP<>(cup.getNamePattern(), cup.getExpSupOfPattern()));
                k--;
            }
        }
    }

    @Override
    public T1 removeDuplicates(String input) {

        String[] elements = input.split(",\\s*");
        StringBuilder result = new StringBuilder();

        for (String element : elements) {
            if (!result.toString().contains(element)) {
                if (!result.isEmpty()) {
                    result.append(", ");
                }
                result.append(element);
            }
        }

        return (T1) String.valueOf(result);

    }

    @Override
    public List<Tep<T2, T3>> combineTep(List<Tep<T2, T3>> tepListX, List<Tep<T2, T3>> tepListY){

        List<Tep<T2, T3>> tepListXY = new ArrayList<>();
        int i = 0;
        int j = 0;

        while(i < tepListX.size() && j < tepListY.size()){
            Tep<T2, T3> tX = tepListX.get(i);
            Tep<T2, T3> tY = tepListY.get(j);

            if(tX.getTID().intValue() < tY.getTID().intValue()){
                i++;
            }
            else if(tX.getTID().intValue() > tY.getTID().intValue()){
                j++;
            }
            else{

                Double temp = Double.valueOf(Math.round((tX.getExistentialProbability().doubleValue()*tY.getExistentialProbability().doubleValue())*100.0)/100.0);
                T3 tepXY = (T3) temp;

                tepListXY.add(new Tep<T2, T3>(tX.getTID(), tepXY));
                i++;
                j++;
            }
        }

        return tepListXY;
    }

    @Override
    public Cup<T1, T2, T3> combineCup(Cup<T1, T2, T3> cupX, Cup<T1, T2, T3> cupY) {

//        if (cupX.getNamePattern().equals("58") && cupY.getNamePattern().equals("52")){
//            System.out.println(cupX.getTEPList());
//            System.out.println(cupY.getTEPList());
//        }

        //combine tep list
        List<Tep<T2, T3>> tepListXY = combineTep(cupX.getTEPList(), cupY.getTEPList());
//        if (cupX.getNamePattern().equals("58") && cupY.getNamePattern().equals("52")){
//            System.out.println(tepListXY);
//        }


        //combine name Cup X with name Cup Y --> name of Cup XY
        T1 nameXY = removeDuplicates( cupX.getNamePattern()+", "+cupY.getNamePattern());

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

    private void TUFPSearchHelperMethod(Cup<T1, T2, T3> combined, int k) {

        //insert new UFP into top-k
        topUFP.add(new UFP<T1, T3>(combined.getNamePattern(), combined.getExpSupOfPattern()));
        //use Comparator to sort topUFP
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

    private Cup<T1, T2, T3> combineHelper(Cup<T1,T2,T3> cupX, Cup<T1,T2,T3> cupY, int k) {

        Cup<T1, T2, T3> combined = combineCup(cupX, cupY);
        candidates++;
        if (combined.getExpSupOfPattern().doubleValue() > threshold.doubleValue()) {
            TUFPSearchHelperMethod(combined,k);
        }
        return combined;
    }

    @Override
    public void TUFPSearch(List<Cup<T1, T2, T3>> queue, int k, int numberFirstLetter) {

        List<Cup<T1, T2, T3>> queueTemp = new ArrayList<>();
        double overestimate = 0.0;

        for (int i = 0; i < queue.size() - 1; i++) {

            for (int j = i + 1; j < queue.size(); j++) {
                if (numberFirstLetter == 0) {

                    overestimate = queue.get(i).getExpSupOfPattern().doubleValue() * queue.get(j).getMax().doubleValue();

                    if (overestimate < threshold.doubleValue()) {
                        return;
                    }
                    queueTemp.add(combineHelper(queue.get(i), queue.get(j),k));
                }else {

                    String letterNameX = String.valueOf(queue.get(i).getNamePattern()).split(", ")[numberFirstLetter];
                    String letterNameY = String.valueOf(queue.get(j).getNamePattern()).split(", ")[numberFirstLetter];

                    if (letterNameX.equals(letterNameY)) {
                        overestimate = queue.get(i).getExpSupOfPattern().doubleValue() * queue.get(j).getMax().doubleValue();
                        if (overestimate < threshold.doubleValue()) {
                            return;
                        }
                        queueTemp.add(combineHelper(queue.get(i), queue.get(j),k));
                    } else {
                        break;
                    }
                }
            }
        }
        numberFirstLetter++;
        TUFPSearch(queueTemp,k,numberFirstLetter);
    }


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


        // error handle if cupl NULL
        if(cupl == null){
            System.out.println("There isn't CUP List which was created. STOP ALGORITHM !!!");
            System.exit(0);
        }

        for(int i=0; i<cupl.size() && i<k; i++){
            currentCup.add(cupl.get(i));
        }

        candidates = currentCup.size();

        //set threshold
        threshold = topUFP.get(topUFP.size()-1).getExpSupOfPattern();

        //numberFirstLetter
        int numberFirstLetter = 0;


        // check memory usage
        MemoryLogger.getInstance().checkMemory();

        //start searching
        TUFPSearch(currentCup,k,numberFirstLetter);

        // save the end time
        endTimeStamp = System.currentTimeMillis();

    }

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
