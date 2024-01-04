package main.interfaces;

import main.objects.Tep;

import java.util.List;


/** an cup object
 * Generic type:
 * T1 is type of item' name
 * T2 is type of TID in TEP
 * T3 is type of existential probability, expected support
 */
public interface CupInterface <T1, T2, T3> {
    //set expected support of pattern
    public void setExpSupOfPattern(T3 prob);

    //set max value of pattern
    public void setMax(T3 prob);

    //get name pattern
    public T1 getNamePattern();

    //get expected support of pattern
    public T3 getExpSupOfPattern();

    // get tep list of pattern
    public List<Tep<T2, T3>> getTEPList();

    // get max value of pattern
    public T3 getMax();
}
