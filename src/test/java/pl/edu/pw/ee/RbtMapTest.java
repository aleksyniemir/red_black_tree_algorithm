package pl.edu.pw.ee;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class RbtMapTest {
    
    @Test
    public void duplicateElemTest() {
        RbtMap<String, Integer> tree = new RbtMap<>();
        tree.setValue("a", 1);
        tree.setValue("b", 4);
        assertEquals(4, (int) tree.getValue("b"));
    }
}
