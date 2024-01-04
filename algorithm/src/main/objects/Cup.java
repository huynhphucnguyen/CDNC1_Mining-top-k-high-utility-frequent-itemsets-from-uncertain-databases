package main.objects;


import main.interfaces.CupInterface;

import java.util.List;

/**
 * a Cup object
 * @param <T1>  is type of item' name
 * @param <T2>  is type of TID in TEP
 * @param <T3>  is type of existential probability, expected support
 */
public class Cup<T1, T2, T3> implements CupInterface<T1, T2, T3> {
    T1 namePattern;
    T3 expSupOfPattern;
    List<Tep<T2, T3>> Tep;
    T3 Max;

    /**
     * contructor method
     * @param namePattern name of cup
     * @param expSupOfPattern expSup of cup
     * @param Tep tep list in cup
     * @param Max max value in tep
     */
    public Cup(T1 namePattern, T3 expSupOfPattern, List<Tep<T2, T3>> Tep, T3 Max){
        this.namePattern = namePattern;
        this.expSupOfPattern = expSupOfPattern;
        this.Tep = Tep;
        this.Max = Max;
    }
    @Override
    public void setExpSupOfPattern(T3 prob){
        expSupOfPattern = prob;
    }

    @Override
    public void setMax(T3 prob){
        Max = prob;
    }

    //get name of cup
    @Override
    public T1 getNamePattern() {
        return namePattern;
    }

    //get expSup of cup
    @Override
    public T3 getExpSupOfPattern() {
        return expSupOfPattern;
    }

    //get tep list of cup
    @Override
    public List<Tep<T2, T3>> getTEPList() {
        return Tep;
    }

    //get max value in tep
    @Override
    public T3 getMax() {
        return Max;
    }

    //use to print cup
    @Override
    public String toString() {
        return "NamePattern: " + namePattern + "\n expSupOfPattern: " + expSupOfPattern + "\n TEPList: \n" + Tep + "\n Max: " + Max + "\n";
    }
}
