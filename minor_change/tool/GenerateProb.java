import java.io.*;
import java.util.*;

public class GenerateProb {

    private static List<List<String>> transactions = new ArrayList<>();

    private static List<String> items = new ArrayList<>();

    /**
     * method generate probability for item
     * @param filePath path of dataset file
     * @throws IOException error
     */
    private static void generateProb(String filePath) throws IOException {

        Random random = new Random();
        // for each transaction
        for (List<String> transaction : transactions){
            // probabilities of items in a transaction
            List<String> probList = new LinkedList<>();
            int i = 0;
            // for each item
            for (String item: items) {
                if (i < transaction.size()) {
                    // check item in transaction
                    if (item.equals(transaction.get(i))) {
                        i++;
                        // Generating random doubles
                        double randomDouble = random.nextDouble();
                        probList.add(String.format("%.2f", randomDouble));
                    } else {
                        probList.add("0");
                    }
                }else {
                    probList.add("0");
                }
            }
            writeProb(filePath,probList);

        }
        System.out.println("The output was write in file at folder 'data'");

    }

    /**
     * method used to write the item in to file
     * @param filePath path of the output file
     * @throws IOException error
     */
    private static void writeItem(String filePath) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write(String.join(" ", items));
            writer.newLine();
        }
    }

    /**
     * method used to write the item in to file
     * @param filePath path of the output file
     * @param prob probabilities of items in a transaction
     * @throws IOException error
     */
    private static void writeProb(String filePath, List<String> prob) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
                writer.write(String.join(" ", prob));
                writer.newLine();
        }
    }

    /**
     * method used to read input file to get distinct items
     * @param filePath path of the dataset file
     */
    private static void readFile(String filePath) {

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            
            String line;
            //read each line
            while ((line = reader.readLine())!= null) {

                //split was separated by ','; space; tab or ', ' to array
                String[] transaction = line.split(",\\s|\\s|\\t|,");
                // convert to list
                transactions.add(Arrays.asList(transaction));
                //check items in transaction are in result or not
                if (!items.containsAll(Arrays.asList(transaction))){
                    // check each item in transaction
                    for (int i=0; i<transaction.length; i++){
                        if (!items.contains(transaction[i])){
                            items.add(transaction[i]);
                        }
                    }
                }

            }
            items.sort(Comparator.comparingInt(Integer::parseInt)); // Sort items in ascending order

        } catch (IOException e) {
            // stop if file not found
            System.out.println("File not found: " + e.getMessage());
            System.out.println("STOP ALGORITHM !!!");
            System.exit(0);
        }
        
    }

    public static void main(String[] args) throws IOException {

        String input = "foodmart.txt";
        String output = "../data/input_foodmart.txt";

        // get items
        readFile(input);

        //write item into output file
        writeItem(output);

        // random and write probability of item into output file
        generateProb(output);

    }
}