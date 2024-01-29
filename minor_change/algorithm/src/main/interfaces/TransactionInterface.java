package main.interfaces;

import java.util.List;

/**
 * Interface of transaction
 * @param <T1> type of Item in transaction
 * @param <T2> type of existential probability in transaction
 */
public interface TransactionInterface <T1, T2>{

    // getters for Transaction
    public List<T1> getItems();

    public List<T2> getProbabilities();

}