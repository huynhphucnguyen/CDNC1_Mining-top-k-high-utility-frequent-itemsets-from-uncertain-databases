package cupList;

import java.util.List;
/** Interface of Cup
 * Generic type:
 * T1 is type of item'name
 * T2 is type of TID in TEP
 * T3 is type of existensial probability, expected support, max value
 */
public interface cupInfo <T1, T2, T3> {
    //get name of cup
    public T1 getNamePattern();
    //get expSup of cup
    public T3 getExpSupOfPattern();
    //get tep list of cup
    public List<Tep<T2, T3>> getTEPList();
    //get max in tep of cup
    public T3 getMax();
}
