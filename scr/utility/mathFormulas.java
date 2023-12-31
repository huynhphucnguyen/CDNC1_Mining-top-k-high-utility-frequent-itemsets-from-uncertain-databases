package utility;

import java.util.List;

import cupList.Tep;

/**
 * This is an class contain the method to cal expected support and tep XY
 * Use the generic type
 * T1 is type of TID in tep
 * T2 is type of existential probability (Prob) in tep
 */


public class mathFormulas<T1 extends Comparable<T1>, T2 extends Number> {

    /**
     * Calculate the expSup of an item or more in a transaction.
     * @param tep tep list of cup
     * @return expected support
     */
    
    public T2 expSup(List<Tep<T1, T2>> tep) {
        //contruct sum with value is null
        T2 sum = null;

        if (tep.isEmpty()){
            Double zeroValue = 0.0;
            return (T2) zeroValue;
        }
        //for each tep
        for (Tep<T1, T2> t : tep) {
            //if sum equal null
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
        // Iterate over tepX and ensure i is also a valid index for tepY
            
        for(Tep<T1, T2> tX : tepX){
            if (i<tepY.size()) {
                Tep<T1, T2> tY = tepY.get(i);
                if(tX.getTID().compareTo(tY.getTID())<0){
                    continue;
                }else if(tX.getTID().compareTo(tY.getTID())>0){
                    i++;
                }else{
                    // Update the result
                    result =  addNumbers(result,
                            (T2) Double.valueOf(
                                tX.getExistensialProbability().doubleValue() * tY.getExistensialProbability().doubleValue()
                            ));
                        i++;
                }
            }
        }
        return result;
    }    

    //add two numbers of generic type (Double).
    private T2 addNumbers(T2 num1, T2 num2) {
        return (T2) Double.valueOf(Math.round((num1.doubleValue() + num2.doubleValue()) * 100.0) / 100.0);
    }
}
