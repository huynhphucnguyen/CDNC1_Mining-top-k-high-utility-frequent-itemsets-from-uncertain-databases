import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;

import TUFP.*;
import cupList.*;

public class Main {
    public static void main(String[] args) {
        String filePath = "../database.txt";
        // int k = 6 top k-1
        TUFP tufp = new TUFP();
        tufp.TUFP(filePath/*, k*/);
        List<Cup<String, Integer, Double>> cupls = tufp.cupl;
        for(Cup<String, Integer, Double> pattern : cupls){
            System.out.println(pattern); //print list of cup (Cup_List)
        }
        for(topK<String, Double> t:tufp.topKUFP){
            System.out.println(t); // print top-k UFP
        }
    }
}