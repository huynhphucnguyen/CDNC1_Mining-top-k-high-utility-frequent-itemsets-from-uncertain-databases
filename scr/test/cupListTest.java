package test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;
import java.util.ArrayList;

public class cupListTest {

    private cupList<String, Integer, Double> cupl;

    @BeforeEach
    public void setUp() {
        cupl = new CupList<>();
    }

    @Test
    public void testCreateCupList() {
        List<String> itemName = new ArrayList<>();
        List<List<Double>> existentialProbability = new ArrayList<>();
        List<cup<String, Integer, Double>> expectedCupList = new ArrayList<>();

        // Populate itemName and existentialProbability with test data
        itemName.add("A");
        itemName.add("B");
        itemName.add("C");
        itemName.add("D");
        itemName.add("E");
        itemName.add("F");
        itemName.add("G");
        itemName.add("H");
        
        String[] lines = {
            "1 0 0.9 0.6 0 0 0 0",
            "0.9 0.9 0.7 0.6 0.4 0 0 0",
            "0 0.5 0.8 0.9 0 0.2 0.4 0",
            "0 0 0.9 0 0.1 0.5 0 0.8",
            "0.4 0.5 0.9 0.3 0 0 0.3 0.3",
            "0 0 0 0.9 0.1 0.6 0 0.3",
            "0.9 0.7 0.4 0.6 0 0.9 0 0"
        };
        for (String line : lines) {
            String[] values = line.split(" ");
            List<Double> ep = new ArrayList<>();
            for (String value : values) {
                ep.add(Double.parseDouble(value));
            }
            existentialProbability.add(ep);
        }

        List<cup<String, Integer, Double>> cupl = cupList.createCupList(itemName, existentialProbability);

        assertEquals(expectedSize, cupl.size());
    }
}

