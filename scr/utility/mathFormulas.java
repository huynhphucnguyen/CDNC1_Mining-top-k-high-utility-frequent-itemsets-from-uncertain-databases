package utility;

import cupList.tepList;

public class mathFormulas {
    
    //cal the expSup of an item or more in transaction.
    public T expSup(tepList<T> tep){
        T sum = null;
        for(tepList<T> t:tep){
            sum += t.getEsitensialProbability();
        }
        return sum;
    }

    public double overestimateExpSup(){

    }
}
