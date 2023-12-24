package cupList;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import utility.mathFormulas;

/** Use to generate list of sigle cup
 * Generic type:
 * T1 is type of item'name
 * T2 is type of TID in TEP
 * T3 is type of existensial probability, expected support
 */
public class cupList<T1, T2, T3 extends Comparable<T3>> {
    List<Cup<T1, T2, T3>> cupl;

    /**
     * create a list of sigle cup from the item's name and existensial probability of item
     * @param itemName list of item's name
     * @param existentialProbability list Prob of an item
     * @return a list of sigle cup
     */

    public List<Cup<T1, T2, T3>> createCupList(List<T1> itemName, List<List<T3>> existentialProbability){
        
        cupl = new ArrayList<>();

        //create tep list in a cup
        for(int i = 0; i < itemName.size(); i++){
            T1 name = (T1) String.valueOf(itemName.get(i)); //get item name
            List<Tep<T2, T3>> tep = new ArrayList<>();
            Integer TID = 1; //transaction ID

            //for each existensial probability
            for(List<T3> epList : existentialProbability){
                T3 epValue = epList.get(i); //get ep in each TID of each item at index i
                if(!epList.get(i).equals(0.0)){ //check ep == 0, then do not add 
                    tep.add(new Tep<T2, T3>((T2) TID, epValue));
                }
                TID = TID + 1; //increment transaction ID
            }

            mathFormulas math = new mathFormulas<>();
            T3 expSupValue = (T3) math.expSup(tep); //cal expSup of item

            cupl.add(new Cup<T1, T2, T3>(name, expSupValue, tep, findMax(tep))); //add a new cup into cup list
        }
        //sort descending for expSup
        selectionSort(cupl);
        return cupl;
    }

    /**
     * find the max of expSup in tep of cup
     * @param list this is tep list of a cup
     * @return max value in tep
     */
    
    public T3 findMax(List<Tep<T2, T3>> list){
        //create max as the first Prob value in tep
        T3 max = list.get(0).getExistensialProbability();
        //for each item in tep
        for (Tep<T2, T3> item : list) {
            //if existensial probability of item > max
            if (item.getExistensialProbability().compareTo(max) > 0) {
                //change max
                max = item.getExistensialProbability();
            }
        }
        return max;
    }

    /**
     * sort cup decrease use selection sort
     * @param cupl list of sigle cup
     */
    public void selectionSort(List<Cup<T1, T2, T3>> cupl) {
        int n = cupl.size();
        for (int i = 0; i < n - 1; i++) {
            int maxIndex = i;
            for (int j = i + 1; j < n; j++) {
                //compare expSup of two cup
                if (cupl.get(j).getExpSupOfPattern().compareTo(cupl.get(maxIndex).getExpSupOfPattern()) > 0) {
                    maxIndex = j;
                }
            }
            // Swap the cups at i and maxIndex
            Cup<T1, T2, T3> temp = cupl.get(i);
            cupl.set(i, cupl.get(maxIndex));
            cupl.set(maxIndex, temp);
        }
    }

    @Override
    public String toString(){
        return cupl + "\n";
    }
}

