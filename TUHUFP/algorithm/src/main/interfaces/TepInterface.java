package main.interfaces;

/**
 * Interface of Tep
 * @param <T1> type of TID (transaction ID) and utility
 * @param <T2> type of existential probability
 */
public interface TepInterface <T1, T2>{

    //getters for TEP
    public T1 getTID();

    public T2 getExistentialProbability();

    public T1 getUtility();

    public T1 getTransUtil();



}
