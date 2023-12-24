package cupList;


/** an cup object
 * Generic type:
 * T1 is type of TID in TEP
 * T2 is type of existensial probability, expected support
 */
public class Tep<T1, T2>{
    T1 TID;
    T2 existentialProbability;

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
        
    //get existensial probability
    public T2 getExistensialProbability(){
        return this.existentialProbability;
    } 

    @Override
    public String toString(){
        return TID + ": " + existentialProbability;
    }
}