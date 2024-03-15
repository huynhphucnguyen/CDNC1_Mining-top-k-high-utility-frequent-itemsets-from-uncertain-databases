package main.interfaces;

import main.objects.Cup;

import java.util.List;

/**
 * the interface class of TUHUFP Algorithm
 * @param <T1> type of name of patterns
 * @param <T2> type of the value of utilities
 * @param <T3> type of existential probability, expected support
 */
public interface AlgorithmTUHUFPInterface <T1, T2, T3>{
    // read data from database file
    public void readData(String filePath, double percentage, int k);
    // use searching top-k UHUFPs
    public void TUHUFPSearch(List<Cup<T1, T2, T3>> queue, int k);
    // run TUHUFP algorithm
    public void runTUHUFPAlgorithm(String filePath, double percentage, int k);

}
