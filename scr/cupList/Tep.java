package cupList;
//Generic type T1 T2 T3
public class Tep<T1, T2>{
    T1 TID;
    T2 existentialProbability;

    public Tep(T1 TID, T2 existentialProbability){
        this.TID = TID;
        this.existentialProbability = existentialProbability;
    }

    public T1 getTID(){
        return this.TID;
    }
        
    
    public T2 getExistensialProbability(){
        return this.existentialProbability;
    } 

    @Override
    public String toString(){
        return TID + ": " + existentialProbability;
    }
}