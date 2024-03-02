package main.interfaces;

import main.objects.Cup;

import java.util.List;

public interface AlgorithmTUFPInterface <T1, T2, T3>{
    // read data from database file
    public void readData(String filePath, double percentage, int k);
    // use searching top-k UFPs
    public void TUFPSearch(List<Cup<T1, T2, T3>> queue, int k);
    // run TUFP algorithm
    public void runTUFPAlgorithm(String filePath, double percentage, int k);

}
