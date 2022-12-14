package pl.edu.pw.ee;

import static org.junit.Assert.assertEquals;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import org.junit.Test;

public class RedBlackTreeTest {
    
    @Test
    public void getPreOrderTest () {
        RedBlackTree<String, Integer> redBlackTree = new RedBlackTree<>();
        redBlackTree.put("kurczak", 1);
        redBlackTree.put("z", 2);
        redBlackTree.put("miesem", 3);
        redBlackTree.put("i", 4);
        redBlackTree.put("sosem", 5);
        redBlackTree.put("smietankowym", 6);
        assertEquals("sosem:5 miesem:3 kurczak:1 i:4 smietankowym:6 z:2 ", redBlackTree.getPreOrder());
    }

    @Test
    public void getPostOrderTest () {
        RedBlackTree<String, Integer> redBlackTree = new RedBlackTree<>();
        redBlackTree.put("kurczak", 1);
        redBlackTree.put("z", 2);
        redBlackTree.put("miesem", 3);
        redBlackTree.put("i", 4);
        redBlackTree.put("sosem", 5);
        redBlackTree.put("smietankowym", 6);
        assertEquals("i:4 kurczak:1 smietankowym:6 miesem:3 z:2 sosem:5 ", redBlackTree.getPostOrder());
    }

    @Test
    public void getInOrderTest () {
        RedBlackTree<String, Integer> redBlackTree = new RedBlackTree<>();
        redBlackTree.put("kurczak", 1);
        redBlackTree.put("z", 2);
        redBlackTree.put("miesem", 3);
        redBlackTree.put("i", 4);
        redBlackTree.put("sosem", 5);
        redBlackTree.put("smietankowym", 6);
        assertEquals("i:4 kurczak:1 miesem:3 smietankowym:6 sosem:5 z:2 ", redBlackTree.getInOrder());
    }

    
    @Test(expected = IllegalArgumentException.class)
    public void putNullValueTest() {
        RedBlackTree<String, Integer> tree = new RedBlackTree<>();
        tree.put("something", null);
        assert false;
    }

    @Test
    public void getNonExistingValueTest() {
        RedBlackTree<String, Integer> tree = new RedBlackTree<>();
        assertEquals(null, tree.get("something"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void putNullKeyTest() {
        RedBlackTree<String, Integer> tree = new RedBlackTree<>();
        tree.put(null, 1);
        assert false;
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void getNullTest() {
        RedBlackTree<String, Integer> tree = new RedBlackTree<>();
        tree.get(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void deleteNonExistingElement() {
        RedBlackTree<String, Integer> tree = new RedBlackTree<>();
        tree.deleteMax();
    }

    @Test
    public void putRecurencyTest() {
        RedBlackTree<String, Integer> tree = new RedBlackTree<>();
        int i = 0, j, count = 0;
        String word;
        File result = new File("result.txt");
        try (BufferedReader br = new BufferedReader(new FileReader("words.txt"))) {
            PrintWriter out = new PrintWriter(new FileWriter(result, true));
            while ((word = br.readLine()) != null) {
                for(j = 0; j < 10; j++){
                    tree.put(word.concat(String.valueOf(j)), i);
                    i++;
                    if(count < tree.getCount()){
                        count = tree.getCount();
                        out.append(count + " " + i + "\n");
                    }
                }
            }
            out.close();
        } catch (IOException e) {
            System.out.println("Problem with opening or reading from a file with data.");
        }
    }
}
