package main.objects;

import main.interfaces.UFPInterface;

/**
 * an UFP object with pattern's name and expected support
 * @param <T1> type of pattern's name
 * @param <T2> type of expected support
 */
public class UFP<T1, T2, T3> implements UFPInterface<T1, T2, T3> {
    T1 namePattern;
    T3 expSupOfPattern;
    T2 utility;

    //constructor method
    public UFP(T1 namePattern, T3 expSupOfPattern, T2 utility){
        this.namePattern = namePattern;
        this.expSupOfPattern = expSupOfPattern;
        this.utility = utility;
    }

    //getters for name and expected support
    @Override
    public T1 getNamePattern() {
        return namePattern;
    }
    @Override
    public T3 getExpSupOfPattern(){
        return expSupOfPattern;
    }
    @Override
    public T2 getUtility() { return utility; }

    @Override
    public String toString(){
        return namePattern + " : " + expSupOfPattern + " : " + utility + "\n";
    }
}
