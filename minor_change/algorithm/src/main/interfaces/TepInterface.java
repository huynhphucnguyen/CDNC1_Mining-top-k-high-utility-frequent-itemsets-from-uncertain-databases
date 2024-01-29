package main.interfaces;

/**
 * Interface of Tep
 * @param <T> type of TID (transaction ID) and utility
 */
public interface TepInterface <T>{

    //getters for TEP
    public T getTID();

    public T getUtility();

    public T getTransUtility();

}
