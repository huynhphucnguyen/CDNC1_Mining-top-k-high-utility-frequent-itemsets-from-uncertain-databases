package cupList;

public class tepList<T1, T2>{
    T1 TID;
    T2 existensialProbability;

    public tepList(T1 TID, T2 existensialProbability){
        this.TID = TID;
        this.existensialProbability = existensialProbability;
    }

    public T1 getTID(){
        return this.TID;
    }
        
    
    public T2 getEsitensialProbability(){
        return this.existensialProbability;
    } 

    @Override
    public String toString(){
        return TID + " " + existensialProbability + "\n";
    }
}