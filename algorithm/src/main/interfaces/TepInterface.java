package main.interfaces;

/**
 * Interface of Tep
 * @param <T1> type of TID (transaction ID)
 * @param <T2> type of existential probability
 */
public interface TepInterface <T1, T2>{

    public T1 getTID();

    public T2 getExistentialProbability();

}
