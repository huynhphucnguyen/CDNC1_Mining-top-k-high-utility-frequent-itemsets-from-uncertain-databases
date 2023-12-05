package cupList;

import java.util.ArrayList;
import java.util.List;

import utility.mathFormulas;

public class cupList<T> implements cupListInterface<T>{
    T namePattern;
    T expSupOfPattern;
    List<tepList<T>> tep;
    T max ; 
    
    public cupList(){
        namePattern = "";
        expSupOfPattern = null;
        tep = new ArrayList<>();
        max = null;
    }

    @Override
    public void addExpSup(tepList<T> tep){
        expSupOfPattern = mathFormulas.expSup(tep);
    }

    @Override
    public void addPattern(T item){
        pattern.addItem(item);
        namePattern = item;
    }
        
    @Override
    public void addTepList(int TID, T existensialProbability){
        
    }
}
