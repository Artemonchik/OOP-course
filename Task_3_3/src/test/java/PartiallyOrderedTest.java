import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class PartiallyOrderedTest {
    PartiallyOrderedSet<String> set;

    @Before
    public void initSet() {
        set = new PartiallyOrderedSet<>();
        set.insert("Mew", "PAK");
        set.insert("PAK", "OOP");
        set.insert("OOP", "DMath");
        set.insert("OOP", "Gaf");
        set.insert("TFKP", "OOP");
        set.insert("DMath", "Math");
        set.insert("DMath", "IP");
    }

    @Test
    public void testOrdering() {

        assertTrue(set.greater("Mew", "PAK"));
        assertTrue(set.greater("Mew", "OOP"));
        assertTrue(set.greater("Mew", "DMath"));
        assertTrue(set.greater("Mew", "Math"));
        assertTrue(set.greater("Mew", "IP"));
        assertTrue(set.greater("Mew", "Gaf"));


        assertTrue(set.greater("PAK", "OOP"));
        assertTrue(set.greater("PAK", "DMath"));
        assertTrue(set.greater("PAK", "Math"));
        assertTrue(set.greater("PAK", "IP"));
        assertTrue(set.greater("PAK", "Gaf"));

        assertTrue(set.greater("OOP", "OOP"));
        assertTrue(set.greater("OOP", "DMath"));
        assertTrue(set.greater("OOP", "Math"));
        assertTrue(set.greater("OOP", "IP"));
        assertTrue(set.greater("OOP", "Gaf"));

        assertTrue(set.greater("DMath", "DMath"));
        assertTrue(set.greater("DMath", "Math"));
        assertTrue(set.greater("DMath", "IP"));

        assertFalse(set.greater("IP", "DMath"));
        assertFalse(set.greater("IP", "OOP"));
        assertFalse(set.greater("IP", "OOP"));
        assertFalse(set.greater("IP", "PAK"));
        assertFalse(set.greater("IP", "Mew"));

        assertFalse(set.greater("PAK", "TFKP"));
        assertFalse(set.greater("TFKP", "PAK"));

        assertFalse(set.greater("OOP", "TFKP"));
        assertTrue(set.greater("TFKP", "OOP"));
    }

    @Test
    public void deleteTest() {
        PartiallyOrderedSet<String> set = new PartiallyOrderedSet<>();
        set.insert("1", "4");
        set.insert("2", "4");
        for (int i = 0; i < 100; i++) {
            set.insert("3", "4");
            set.insert("4", "5");
            set.insert("4", "6");
            set.insert("4", "7");
        }

        set.delete("4");
        assertTrue(set.greater("1", "5"));
        assertTrue(set.greater("1", "6"));
        assertTrue(set.greater("1", "7"));
        assertTrue(set.greater("3", "5"));
        assertTrue(set.greater("2", "7"));
    }

    @Test
    public void minimal_maximumTest() {
        String[] topSorted = set.minimal().toArray(new String[0]);
        Arrays.sort(topSorted);
        String[] expected = new String[]{"Gaf", "Math", "IP"};
        Arrays.sort(expected);
        assertArrayEquals(expected, topSorted);

        String[] maximums = set.maximum().toArray(new String[0]);
        Arrays.sort(maximums);
        String[] expectedM = new String[]{"Mew", "TFKP"};
        Arrays.sort(expectedM);
        assertArrayEquals(expectedM, maximums);
    }

    @Test
    public void topSortTest() {
        ArrayList<String> sorted = set.topSort();
        assertEquals(8, sorted.size());
        for (String elem1 : sorted) {
            for (String elem2 : sorted) {
                if (set.greater(elem1, elem2)) {
                    assertTrue(sorted.indexOf(elem1) >= sorted.indexOf(elem2));
                }
            }
        }
    }

    @Test
    public void linearTest(){
        assertFalse(set.isLinear());

        PartiallyOrderedSet<String> set = new PartiallyOrderedSet<>();
        set.insert("f", "e");
        set.insert("g", "e");
        set.insert("e", "d");
        set.insert("d", "b");
        set.insert("d", "c");
        set.insert("b", "a");
        set.insert("c", "a");
        assertFalse(set.isLinear());
        set.delete("b");
        assertFalse(set.isLinear());
        set.delete("f");
        assertTrue(set.isLinear());
        set.delete("f");
        set.delete("e");
        set.delete("d");
        set.delete("d");
        set.delete("g");
        set.delete("d");
        set.delete("c");
        set.delete("a");
        assertTrue(set.isLinear());
    }

    @Test
    public void latticeOrderedTest(){
        assertFalse(set.isLattice());
        PartiallyOrderedSet<String> set = new PartiallyOrderedSet<>();
        set.insert("e", "d");
        set.insert("e", "f");
        set.insert("e", "g");
        set.insert("d", "c");
        set.insert("f", "h");
        set.insert("g", "h");
        set.insert("h", "i");
        set.insert("i", "a");
        assertFalse(set.isLattice());
        set.insert("d", "c");
        set.insert("c", "b");
        set.insert("b", "a");
        assertTrue(set.isLattice());
    }

    @Test
    public void errorWhenTriesToMakeCircle(){
        try {
            set.insert("DMath", "Mew");
            fail("Must be error as tried to make circle in Partially Ordered Set");
        }catch (IllegalArgumentException ignored){

        }
    }
    @Test
    public void comparingWithElementNotInSet(){
        try {
            set.greater("Arrrrr i'm a TIGER", "Mew");
            fail("Must be error as tried to compare with element not in Partially Ordered Set");
        }catch (IllegalArgumentException ignored){

        }
    }
    @Test
    public void solutionFromTheTaskExample(){
        PartiallyOrderedSet<String> set = new PartiallyOrderedSet<>();
        set.insert("Мария", "Василий");
        set.insert("Вероника", "Василий");
        set.insert("Василий", "Татьяна");
        set.insert("Дмитрий");

        String[] expected = {"Мария", "Вероника", "Дмитрий"};
        Arrays.sort(expected);

        String[] smartest = set.maximum().toArray(new String[0]);
        Arrays.sort(smartest);

        assertArrayEquals(expected, smartest);
    }
}
