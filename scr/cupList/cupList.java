package cupList;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import utility.mathFormulas;

public class cupList<T1, T2, T3 extends Comparable<T3>> {
    List<cup<String, Integer, Double>> cupl;

    public List<cup<String, Integer, Double>> createCupList(List<T1> itemName, List<List<T3>> existentialProbability){
        
        cupl = new ArrayList<>();

        for(int i = 0; i < itemName.size(); i++){
            String name = String.valueOf(itemName.get(i));
            List<tepList<Integer, Double>> tep = new ArrayList<>();

            for(List<T3> epList : existentialProbability){
                // 
                if(epList.isEmpty() || !epList.get(0).equals(0)){ //
                    T3 epValue = epList.get(0);
                // 
                    if (epValue instanceof Double) {
                        tep.add(new tepList<Integer, Double>(i + 1, (Double) epValue));
                    }
                }
            }

            mathFormulas<Integer, Double> math = new mathFormulas<>();
            cupl.add(new cup<String, Integer, Double>(name, math.expSup(tep), tep, new cupList<String, Integer, Double>().findMax(tep)));
        }
        return cupl;
    }

    public T3 findMax(List<tepList<T2, T3>> list){
        T3 max = list.get(0).getEsitensialProbability();
        for (tepList<T2, T3> item : list) {
            if (item.getEsitensialProbability().compareTo(max) > 0) {
                max = item.getEsitensialProbability();
            }
        }
        return max;
    }

    @Override
    public String toString(){
        return cupl + "\n";
    }
}

