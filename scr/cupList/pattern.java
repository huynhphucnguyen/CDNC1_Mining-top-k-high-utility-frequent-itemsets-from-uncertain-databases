package cupList;

import java.util.ArrayList;
import java.util.List;

public class pattern <T>{
    List<T> items;
    
    public pattern(){
        items = new ArrayList<>();
    }

    public void addItem(T item) {
        items.add(item);
    }
    
}
