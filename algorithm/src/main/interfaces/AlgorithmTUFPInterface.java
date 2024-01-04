package main.interfaces;

import main.objects.Cup;
import main.objects.Tep;
import main.objects.Transaction;

import java.util.List;

public interface AlgorithmTUFPInterface <T1, T2 extends Number & Comparable<T2>, T3 extends Number & Comparable <T3>>{

    public void readData(String filePath, int k);

    public void createFirstUFPs(List<Transaction<T1, T3>> transactions, int k);

    public T1 removeDuplicates(String input);

    public List<Tep<T2, T3>> combineTep(List<Tep<T2, T3>> tepListX, List<Tep<T2, T3>> tepListY);

    public Cup<T1, T2, T3> combineCup(Cup<T1, T2, T3> cupX, Cup<T1, T2, T3> cupY);

    public void TUFPSearch(List<Cup<T1, T2, T3>> queue, int k, int numberFirstLetter);

    public void runTUFPAlgorithm(String filePath, int k);

}
