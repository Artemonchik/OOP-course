import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class StackTest {
    @Test
    public void simpleTest1(){
        Stack stack = new Stack();
        stack.push(12);
        stack.push(11);
        stack.push(10);
        stack.push(9);
        stack.push(8);
        assertEquals(5, stack.count());
        assertEquals(8, stack.pop());
        assertEquals(9, stack.pop());
        assertEquals(10, stack.pop());
        assertEquals(11, stack.pop());
        assertEquals(12, stack.pop());
        assertEquals(0, stack.count());
    }

    @Test
    public void test100000Elems(){
        Stack stack = new Stack();
        for(int i = 0; i < 100000; i++){
            stack.push(i);
        }
        assertEquals(100000, stack.count());
        for(int i = 100000 - 1 ; i >= 0; i--){
            assertEquals(i, stack.pop());
        }
        assertEquals(0, stack.count());
    }
}
