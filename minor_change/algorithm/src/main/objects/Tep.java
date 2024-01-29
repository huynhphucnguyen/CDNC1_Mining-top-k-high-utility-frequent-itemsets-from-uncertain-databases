package main.objects;

import main.interfaces.TepInterface;

/**
 * a Tep object
 * @param <T>  is type of TID and utility in TEP
 */
public class Tep<T> implements TepInterface<T> {
    private final T TID;
    private final T utility;
    private  final T transUtil;

    /**
     * constructor
     * @param TID transaction Identify
     * @param utility of item in a TID
     * @param transUtil is utility of transaction
     */
    public Tep(T TID, T utility, T transUtil){
        this.TID = TID;
        this.utility = utility;
        this.transUtil = transUtil;
    }

    //get TID of tep
    public T getTID(){
        return this.TID;
    }

    //get utility
    public T getUtility(){
        return this.utility;
    }

    //get transaction utility
    public  T getTransUtility(){ return this.transUtil;}

    @Override
    public String toString(){
        return TID + ": " + utility+ ": " + transUtil;
    }
}
