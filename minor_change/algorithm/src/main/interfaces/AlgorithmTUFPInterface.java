package main.interfaces;

import main.objects.Cup;
import main.objects.Tep;
import main.objects.Transaction;

import java.util.List;

public interface AlgorithmTUFPInterface <T1, T2, T3>{
    // read data from database file
    public void readData(String filePath,double percentage, int k);
    // create top k Cup for searching
//    public void createFirstUFPs(List<Transaction<T1, T3>> transactions, int k);
    // use searching top-k UFPs
    public void TUFPSearch(Cup<T1, T2> cupList,int index, int k);
    // run TUFP algorithm
    public void runTUFPAlgorithm(String filePath, double percentage, int k);

}
