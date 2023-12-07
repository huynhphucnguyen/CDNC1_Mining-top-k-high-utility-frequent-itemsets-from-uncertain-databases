package utility;

import java.util.List;

import cupList.tepList;

public class mathFormulas<T1, T2 extends Number> {

    // Calculate the expSup of an item or more in a transaction.
    public T2 expSup(List<tepList<T1, T2>> tep) {
        T2 sum = null;
        for (tepList<T1, T2> t : tep) {
            if (sum == null) {
                sum = t.getEsitensialProbability();
            } else {
                sum = addNumbers(sum, t.getEsitensialProbability());
            }
        }
        return sum;
    }

    // Helper method to add two numbers of generic type T2.
    private T2 addNumbers(T2 num1, T2 num2) {
        if (num1 instanceof Double && num2 instanceof Double) {
            return (T2) Double.valueOf(num1.doubleValue() + num2.doubleValue());
        } else if (num1 instanceof Integer && num2 instanceof Integer) {
            return (T2) Integer.valueOf(num1.intValue() + num2.intValue());
        }
        // Handle other Number types as needed.
        // You may need to add more cases for other Number subclasses.
        throw new IllegalArgumentException("Unsupported Number type");
    }

    // public double overestimateExpSup(){

    // }
}
