package utility;

import java.util.List;

import cupList.Tep;
//Generic type T1 T2
public class mathFormulas<T1 extends Comparable<T1>, T2 extends Number> {

    // Calculate the expSup of an item or more in a transaction.
    public T2 expSup(List<Tep<T1, T2>> tep) {
        T2 sum = null;
        for (Tep<T1, T2> t : tep) {
            if (sum == null) {
                sum = t.getExistensialProbability();
            } else {
                sum = addNumbers(sum, t.getExistensialProbability());
            }
        }
        return sum;
    }

    //cal expSup of cup X and cup Y
    public T2 expSupXY(List<Tep<T1, T2>> tepX, List<Tep<T1, T2>> tepY){
        T2 result = (T2) Double.valueOf(0.0);
        int i = 0;
        for(Tep<T1, T2> t: tepX){
            if(t.getTID().compareTo(tepY.get(i).getTID()) == 0){
                result =  addNumbers(result,(T2)
                Double.valueOf(t.getExistensialProbability().doubleValue()*tepY.get(i).getExistensialProbability().doubleValue()));
                i++;
            }
        }
        return result;
    }

    //add two numbers of generic type (Double).
    private T2 addNumbers(T2 num1, T2 num2) {
        return (T2) Double.valueOf(Math.round((num1.doubleValue() + num2.doubleValue()) * 100.0) / 100.0);
    }
}
