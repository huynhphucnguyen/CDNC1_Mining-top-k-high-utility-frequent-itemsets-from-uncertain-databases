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

    /**
     * contructor method
     * @param TID transaction ID
     * @param existentialProbability Prob of each TID in tep
     */
    public Tep(T1 TID, T2 existentialProbability){
        this.TID = TID;
        this.existentialProbability = existentialProbability;
    }

    //get TID of tep
    public T1 getTID(){
        return this.TID;
    }

    //get existential probability
    public T2 getExistentialProbability(){
        return this.existentialProbability;
    }

    @Override
    public String toString(){
        return TID + ": " + existentialProbability;
    }
}
