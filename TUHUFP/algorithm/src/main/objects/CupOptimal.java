package main.objects;

import main.interfaces.CupInterface;

import java.util.List;

public class CupOptimal<T1, T2, T3> implements CupInterface<T1, T2, T3> {
    T1 namePattern;
    T3 expSupOfPattern;
    List<Tep<T2, T3>> Tep;
    T3 Max;
    T2 transWeiUtil;
    T2 utility;
    CupOptimal<T1, T2, T3> last;

    // Constructor with default value for last parameter
    public CupOptimal(T1 namePattern, T3 expSupOfPattern, List<Tep<T2, T3>> tepList, T3 max,
                      T2 transWeiUtil, T2 utility) {
        this(namePattern, expSupOfPattern, tepList, max, transWeiUtil, utility, null);
    }

    /**
     * constructor method
     * @param namePattern name of cup
     * @param expSupOfPattern expSup of cup
     * @param Tep tep list in cup
     * @param Max max value in tep
     * @param transWeiUtil (transaction weight utility) sum of all transaction's utilities contain item/itemset X
     */
    public CupOptimal(T1 namePattern, T3 expSupOfPattern, List<Tep<T2, T3>> Tep, T3 Max, T2 transWeiUtil, T2 utility, CupOptimal<T1, T2, T3> last){
        this.namePattern = namePattern;
        this.expSupOfPattern = expSupOfPattern;
        this.Tep = Tep;
        this.Max = Max;
        this.transWeiUtil = transWeiUtil;
        this.utility = utility;
        this.last = last;
    }

    public CupOptimal() {

    }

    @Override
    public void setExpSupOfPattern(T3 prob){
        expSupOfPattern = prob;
    }

    //setters and getter for Cup Optimal
    @Override
    public void setMax(T3 prob){
        Max = prob;
    }

    @Override
    public void setTransWeiUtil(T2 utility){ transWeiUtil = utility; }

    @Override
    public void setUtility(T2 utility) {
        this.utility = utility;
    }

    public void setLast(CupOptimal<T1, T2, T3> cup){this.last = cup;}

    @Override
    public T1 getNamePattern() {
        return namePattern;
    }

    @Override
    public T3 getExpSupOfPattern() {
        return expSupOfPattern;
    }

    @Override
    public List<Tep<T2, T3>> getTEPList() {
        return Tep;
    }

    @Override
    public T3 getMax() {
        return Max;
    }

    @Override
    public  T2 getTransWeiUtil(){ return transWeiUtil; }

    @Override
    public T2 getUtility() {
        return utility;
    }

    public CupOptimal<T1, T2, T3> getLast() { return last;}

    @Override
    public String toString() {
        return "NamePattern: " + namePattern + "\n expSupOfPattern: " + expSupOfPattern + "\n Utility: " + utility +
                "\n TEPList: \n" + Tep + "\n Max: " + Max + "\n TWU: " + transWeiUtil +"\n" ;
    }
}
