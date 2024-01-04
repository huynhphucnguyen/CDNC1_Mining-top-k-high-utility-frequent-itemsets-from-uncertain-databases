import java.io.*;
import java.util.*;

public class GenerateProb {

    private static List<List<String>> transactions = new ArrayList<>();

    private static List<String> items = new ArrayList<>();

    private static void generateProb(String filePath) throws IOException {
        Random random = new Random();
        for (List<String> transaction : transactions){
            List<String> probList = new LinkedList<>();
            int i = 0;
            for (String item: items) {
                if (i < transaction.size()) {
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

    private static void writeItem(String filePath) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write(String.join(" ", items));
            writer.newLine();
        }
    }

    private static void writeProb(String filePath, List<String> prob) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
                writer.write(String.join(" ", prob));
                writer.newLine();
        }
    }

    private static void readFile(String filePath) {

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            
            String line;
            //read each line to get Prob in transaction of item
            while ((line = reader.readLine())!= null) {

                //split was separated by ','; space; tab or ', ' to array
                String[] transaction = line.split(",\\s|\\s|\\t|,");

                transactions.add(Arrays.asList(transaction));

                if (!items.containsAll(Arrays.asList(transaction))){
                    for (int i=0; i<transaction.length; i++){
                        if (!items.contains(transaction[i])){
                            items.add(transaction[i]);

//                            try {
//                                Double.parseDouble(transaction[i]);
//                                items.sort(Comparator.comparingDouble(Double::parseDouble));
//                            } catch (NumberFormatException e) {
//                                continue;
//                            }
                        }
                    }
                }
            }

        } catch (IOException e) {
            System.out.println("File not found: " + e.getMessage());
            System.out.println("STOP ALGORITHM !!!");
            System.exit(0);
        }
        
    }

    public static void main(String[] args) throws IOException {

        String input = "BMS-POS.txt";
        String output = "../data/input_BMS.txt";

        readFile(input);

        writeItem(output);

        generateProb(output);



    }
}