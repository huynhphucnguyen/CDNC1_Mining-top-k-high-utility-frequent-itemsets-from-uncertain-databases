package main.objects;


import main.interfaces.CupInterface;

import java.util.List;

/**
 * a Cup object
 * @param <T1>  is type of item' name
 * @param <T2>  is type of TID, utility and Max
 */
public class Cup<T1, T2> implements CupInterface<T1, T2> {
    T1 namePattern;
    T2 utility;
    List<Tep<T2>> Tep;
    T2 TWU;

    /**
     * constructor method
     * @param namePattern name of cup
     * @param utility expSup of cup
     * @param Tep tep list in cup
     * @param TWU max value in tep
     */
    public Cup(T1 namePattern, T2 utility, List<Tep<T2>> Tep, T2 TWU){
        this.namePattern = namePattern;
        this.utility = utility;
        this.Tep = Tep;
        this.TWU = TWU;
    }
    @Override
    public void setUtil(T2 util){
        utility = util;
    }

    //setters and getter for Cup
    @Override
    public void setTransWeiUtil(T2 util){
        TWU = util;
    }

    @Override
    public T1 getNamePattern() {
        return namePattern;
    }

    @Override
    public T2 getUtil() {
        return utility;
    }

    @Override
    public List<Tep<T2>> getTEPList() {
        return Tep;
    }

    @Override
    public T2 getTransWeiUtil() {
        return TWU;
    }

    @Override
    public String toString() {
        return "NamePattern: " + namePattern + "\n Utility: " + utility + "\n TEPList: \n" + Tep + "\n TWU: " + TWU + "\n";
    }
}
