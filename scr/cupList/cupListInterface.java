package cupList;
/**
 * cupListInterface
 */
public interface cupListInterface <T> {
    public void addPattern(T namePattern);
    public void addExpSup(tepList<T> tep);
    public void addTepList(int TID, T existensialProbability);
    public String toString();
}
