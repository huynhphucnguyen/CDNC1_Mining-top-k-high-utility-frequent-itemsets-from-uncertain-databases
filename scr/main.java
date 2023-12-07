import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;

import TUFP.*;
import cupList.*;

public class main {
    public static void main(String[] args) {
        String filePath = "../database.txt";
        TUFP tufp = new TUFP();
        tufp.readData(filePath);
        List<cup<String, Integer, Double>> cupls = tufp.cupl;
        for(cup<String, Integer, Double> pattern : cupls){
            System.out.println(pattern);
        }
    }
}