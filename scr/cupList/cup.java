package cupList;

import java.util.List;

//Generic type T1 T2 T3
public class Cup<T1, T2, T3> implements cupInfo<T1, T2, T3>{
        T1 namePattern;
        T3 expSupOfPattern;
        List<Tep<T2, T3>> Tep;
        T3 Max;

    public Cup(T1 namePattern, T3 expSupOfPattern, List<Tep<T2, T3>> Tep, T3 Max){
        this.namePattern = namePattern;
        this.expSupOfPattern = expSupOfPattern;
        this.Tep = Tep;
        this.Max = Max;
    }

    @Override
    public T1 getNamePattern() {
        return namePattern;
    }

    @Override
    public T3 getExpSupOfPattern() {
        return expSupOfPattern;
    }

    @Override
    public List<Tep<T2, T3>> getTEPList() {
        return Tep;
    }

    @Override
    public T3 getMax() {
        return Max;
    }


    @Override
    public String toString() {
        return "NamePattern: " + namePattern + "\nexpSupOfPattern: " + expSupOfPattern + "\nTEPList: \n" + Tep + "\n Max: " + Max + "\n";
    }
}
