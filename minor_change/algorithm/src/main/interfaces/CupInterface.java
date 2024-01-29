package main.interfaces;

import main.objects.Tep;

import java.util.List;


/** an cup object
 * Generic type:
 * T1 is type of item' name
 * T2 is type of TID in TEP
 * T3 is type of existential probability, expected support
 */
public interface CupInterface <T1, T2> {
    //set expected support of pattern
    public void setUtil(T2 util);

    //set max value of pattern
    public void setTransWeiUtil(T2 util);

    //get name pattern
    public T1 getNamePattern();

    //get expected support of pattern
    public T2 getUtil();

    // get tep list of pattern
    public List<Tep<T2>> getTEPList();

    // get max value of pattern
    public T2 getTransWeiUtil();
}
