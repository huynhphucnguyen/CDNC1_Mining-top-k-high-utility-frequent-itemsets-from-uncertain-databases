package cupList;

import java.util.List;
//Generic type T1 T2 T3
public interface cupInfo <T1, T2, T3> {
    public T1 getNamePattern();
    public T3 getExpSupOfPattern();
    public List<Tep<T2, T3>> getTEPList();
    public T3 getMax();
}
