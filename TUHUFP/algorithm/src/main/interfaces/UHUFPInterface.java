package main.interfaces;

/**
 * the interface class of Uncertain High Utility Frequent Patterns
 * @param <T1> type of pattern's name
 * @param <T2> type of the utility value
 * @param <T3> type expected support
 */
public interface UHUFPInterface <T1, T2, T3>{

    //getters for UHUFPs
    //public T1 getNamePattern();

    public T3 getExpSupOfPattern();

    //public T2 getUtility();

}
