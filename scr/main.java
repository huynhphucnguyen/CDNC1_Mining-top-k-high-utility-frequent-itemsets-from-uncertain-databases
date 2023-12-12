import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;

import TUFP.*;
import cupList.*;

public class Main {
    public static void main(String[] args) {
        String filePath = "../database.txt";
        TUFP tufp = new TUFP();
        tufp.readData(filePath);
        List<Cup<String, Integer, Double>> cupls = tufp.cupl;
        for(Cup<String, Integer, Double> pattern : cupls){
            System.out.println(pattern);
        }
    }
}