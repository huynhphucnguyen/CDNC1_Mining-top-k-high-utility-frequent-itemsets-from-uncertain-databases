package main.interfaces;

/**
 * Interface of UFP
 * @param <T1> type of pattern's name
 * @param <T2> type of expected support
 */
public interface UFPInterface <T1, T2, T3>{

    //getters for UFP
    public T1 getNamePattern();

    public T3 getExpSupOfPattern();

    public T2 getUtility();

}
