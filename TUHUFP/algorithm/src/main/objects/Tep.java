package main.objects;

import main.interfaces.TepInterface;

/**
 * a Tep object
 * @param <T1>  is type of TID in TEP
 * @param <T2>  is type of existential probability, expected support
 */
public class Tep<T1, T2> implements TepInterface<T1, T2> {
    private final T1 TID;
    private final T2 existentialProbability;
    private  final T1 utility;

    private final  T1 transUtil;

    /**
     * constructor method
     * @param TID transaction ID
     * @param existentialProbability Prob of each TID in tep
     */
    public Tep(T1 TID, T2 existentialProbability, T1 utility, T1 transUtil){
        this.TID = TID;
        this.existentialProbability = existentialProbability;
        this.utility = utility;
        this.transUtil = transUtil;
    }

    //getters of TEP class
    @Override
    public T1 getTID(){
        return this.TID;
    }

    @Override
    public T2 getExistentialProbability(){
        return this.existentialProbability;
    }

    @Override
    public T1 getUtility() {
        return this.utility;
    }

    @Override
    public T1 getTransUtil(){ return this.transUtil; }

    /**
     * Tep toString()
     * @return Tep's information
     */
    @Override
    public String toString(){
        return TID + ": " + existentialProbability + ": " + utility  + ": " + transUtil;
    }
}
