package main.objects;

import main.interfaces.TransactionInterface;

import java.util.List;

/**
 * Transaction object contain list of item and probability's list of item's list
 * @param <T1> tpye of item in a transaction
 * @param <T2> type of probability of item in transaction
 */
public class Transaction <T1, T2> implements TransactionInterface<T1, T2> {
    private List<T1> items;
    private List<T2> probabilities;

    public Transaction(List<T1> items, List<T2> probabilities) {
        this.items = items;
        this.probabilities = probabilities;
    }

    // Getters for items and probabilities

    public List<T1> getItems() {
        return items;
    }

    public List<T2> getProbabilities() {
        return probabilities;
    }

    @Override
    public String toString(){
        return items + "\n" + probabilities;
    }
}
