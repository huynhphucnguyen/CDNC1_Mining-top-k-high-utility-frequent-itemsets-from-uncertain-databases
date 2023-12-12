package utility;

import java.util.List;

import cupList.Tep;
//Generic type T1 T2 T3
public class mathFormulas<T1, T2 extends Number> {

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

    //add two numbers of generic type (Double).
    private T2 addNumbers(T2 num1, T2 num2) {
        return (T2) Double.valueOf(Math.round((num1.doubleValue() + num2.doubleValue()) * 100.0) / 100.0);
    }

    // public double overestimateExpSup(){

    // }
}
