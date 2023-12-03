import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;


class tepListImpl<T> implements tepList<T>{
    private int TID;
    private T existentialProbability;

    public tepListImpl(int TID, T existentialProbability) {
        this.TID = TID;
        this.existentialProbability = existentialProbability;
    }

    @Override
    public int getTID(){
        return this.TID;
    }
    @Override
    public T getExistentialProbability(){
        return this.existentialProbability;
    }
    @Override
    public String toString(){
        return "TID: " + TID + ", Existential Probability: " + existentialProbability;
    }
}



public class cupList<T> {
    private Patterns<T> pattern;
    private List<tepListImpl<T>> tep;
    private double expSup; // Expected support value of pattern
    private T max;    // Maximum existential probability in TEP

    public cupList() {
        pattern = new Patterns<>();
        tep = new ArrayList<>();
        expSup = 0.0;
        max = null;
    }

    public void addPatternItem(T item) {
        pattern.addItem(item);
    }

    public void addTEP(int TID, T existentialProbability) {
        tep.add(new tepListImpl<>(TID, existentialProbability));
        
    }

    @Override
    public String toString() {
        return String.format("Pattern: %s\nTEP List: %s\nMax EP: %.2f", pattern, tep, max);
    }
}
