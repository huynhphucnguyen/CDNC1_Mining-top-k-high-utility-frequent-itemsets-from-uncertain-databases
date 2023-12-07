package cupList;

import java.util.List;

public interface cupInfo <T1, T2, T3> {
    public T1 getNamePattern();
    public T3 getExpSupOfPattern();
    public List<tepList<T2, T3>> getTEPList();
    public T3 getMax();
}
