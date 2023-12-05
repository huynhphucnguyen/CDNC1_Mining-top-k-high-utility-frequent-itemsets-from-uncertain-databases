package cupList;

public class tepList<T>{
    T TID;
    T existensialProbability;

    public tepList(T TID, T existensialProbability){
        this.TID = TID;
        this.existensialProbability = existensialProbability;
    }

    public T getTID()
        return this.TID;
    
    public T getEsitensialProbability()
        return this.existensialProbability;
}