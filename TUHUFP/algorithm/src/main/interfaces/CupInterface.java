package main.interfaces;

import main.objects.Tep;

import java.util.List;


/** a cup object
 * Generic type:
 * T1 is type of item's name
 * T2 is type of TID in TEP, the utility of item, transaction
 * T3 is type of expected support
 */
public interface CupInterface <T1, T2, T3> {
    //set expected support of pattern
    public void setExpSupOfPattern(T3 prob);

    //set max value of pattern
    public void setMax(T3 prob);

    // set TWU (transaction weight utility)
    public void setTransWeiUtil(T2 utility);

    //set utility value
    public void setUtility(T2 utility);

    //get name pattern
    public T1 getNamePattern();

    //get expected support of pattern
    public T3 getExpSupOfPattern();

    // get tep list of pattern
    public List<Tep<T2, T3>> getTEPList();

    // get max value of pattern
    public T3 getMax();

    // get TWU value
    public T2 getTransWeiUtil();

    //get utility value
    public T2 getUtility();
}
