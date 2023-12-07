package cupList;

import java.util.List;

public class cup<T1, T2, T3> implements cupInfo<T1, T2, T3>{
        T1 namePattern;
        T3 expSupOfPattern;
        List<tepList<T2, T3>> tep;
        T3 max;

    public cup(T1 namePattern, T3 expSupOfPattern, List<tepList<T2, T3>> tep, T3 max){
        this.namePattern = namePattern;
        this.expSupOfPattern = expSupOfPattern;
        this.tep = tep;
        this.max = max;
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
    public List<tepList<T2, T3>> getTEPList() {
        return tep;
    }

    @Override
    public T3 getMax() {
        return max;
    }

    @Override
    public String toString() {
        return "NamePattern: " + namePattern + ", expSupOfPattern: " + expSupOfPattern + ", TEPList: " + tep + ", Max: " + max;
    }
}
