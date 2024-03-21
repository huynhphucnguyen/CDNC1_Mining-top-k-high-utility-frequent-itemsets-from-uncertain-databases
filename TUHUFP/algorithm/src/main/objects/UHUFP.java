package main.objects;

import main.interfaces.UHUFPInterface;

/**
 * UHUFP object with pattern's name and expected support
 * @param <T1> type of pattern's name
 * @param <T2> type of utility
 * @param <T3> type of expected support
 */
public class UHUFP<T1, T2, T3> implements UHUFPInterface<T1, T2, T3> {
    private T1 namePattern;
    private T3 expSupOfPattern;
    private T2 utility;

    //constructor method
    public UHUFP(T1 namePattern, T3 expSupOfPattern, T2 utility){
        this.namePattern = namePattern;
        this.expSupOfPattern = expSupOfPattern;
        this.utility = utility;
    }

    // getters
    @Override
    public T3 getExpSupOfPattern(){
        return expSupOfPattern;
    }

    @Override
    public String toString(){
        return namePattern + " : " + expSupOfPattern + " : " + utility + "\n";
    }
}
