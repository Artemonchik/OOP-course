import org.junit.Test;

import static org.junit.jupiter.api.Assertions.*;

import javafx.util.Pair;

import java.util.NoSuchElementException;

public class PQTest {
    @Test
    public void taskExampleTest1() {
        MyPriorityQueue<Integer, String> queue = new MyPriorityQueue<>();
        queue.insert(200, "dog");
        queue.insert(10, "man");
        assertEquals(new Pair<>(200, "dog"), queue.extractMaximum());

        queue.insert(5, "penguin");
        queue.insert(500, "parrot");
        assertEquals(new Pair<>(500, "parrot"), queue.extractMaximum());
        assertEquals(new Pair<>(10, "man"), queue.extractMaximum());
        assertEquals(new Pair<>(5, "penguin"), queue.extractMaximum());
    }

    @Test
    public void testCaseWithMultiplySameKeys() {
        MyPriorityQueue<Integer, String> queue = new MyPriorityQueue<>();
        queue.insert(200, "dog");
        queue.insert(10, "man");
        queue.insert(200, "dog");
        queue.insert(10, "man");
        queue.insert(200, "dog");
        queue.insert(10, "man");
        assertEquals(new Pair<>(200, "dog"), queue.extractMaximum());
        assertEquals(new Pair<>(200, "dog"), queue.extractMaximum());
        assertEquals(new Pair<>(200, "dog"), queue.extractMaximum());
        assertEquals(new Pair<>(10, "man"), queue.extractMaximum());
        assertEquals(new Pair<>(10, "man"), queue.extractMaximum());
        assertEquals(new Pair<>(10, "man"), queue.extractMaximum());
    }

    @Test
    public void nullCase() {
        MyPriorityQueue<Integer, String> queue = new MyPriorityQueue<>();
        queue.insert(200, "dog");
        queue.insert(10, "man");
        queue.extractMaximum();
        queue.extractMaximum();
        try {
            queue.extractMaximum();
            fail("There should be NoSuchElementException error");
        } catch (ArrayStoreException ignored) {

        }
    }

    @Test
    public void testWith2000000Elems() {
        final int RANGE = 1000;
        MyPriorityQueue<Integer, String> queue = new MyPriorityQueue<>();
        for (int i = RANGE; i > -RANGE; i--) {
            queue.insert(i, "Meeeew: " + i);
        }
        for (int i = RANGE; i > -RANGE; i--) {
            assertEquals(new Pair<>(i, "Meeeew: " + i), queue.extractMaximum());
        }
    }

    @Test
    public void iterationTest() {
        MyPriorityQueue<Integer, String> queue = new MyPriorityQueue<>();
        for (int i = 9; i >= 0; i--) {
            queue.insert(i, "mew: " + i);
        }
        int i = 9;
        for (Pair<Integer, String> elem : queue) {
            assertEquals(new Pair<Integer, String>(i, "mew: " + i), elem);
            i--;
        }
    }
}
