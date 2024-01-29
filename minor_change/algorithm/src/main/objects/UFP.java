package main.objects;

import main.interfaces.UFPInterface;

/**
 * an UFP object with pattern's name and expected support
 * @param <T1> type of pattern's name
 * @param <T2> type of utility
 */
public class UFP<T1, T2> implements UFPInterface<T1, T2> {
    T1 namePattern;
    T2 utility;

    //constructor method
    public UFP(T1 namePattern, T2 utility){
        this.namePattern = namePattern;
        this.utility = utility;
    }

    //getters for name and expected support
    public T1 getNamePattern() {
        return namePattern;
    }

    public T2 getUtil(){
        return utility;
    }

    @Override
    public String toString(){
        return namePattern + " : " + utility + "\n";
    }
}
