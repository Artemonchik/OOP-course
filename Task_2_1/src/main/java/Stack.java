import java.lang.reflect.Array;
import java.util.Arrays;

public class Stack {
    private int[] arr;
    private int end;

    /**
     * Create stack with buffer with 10 elem size
     */
    Stack() {
        arr = new int[10];
        end = 0;
    }

    /**
     * @param size initial value of stack buffer
     */
    Stack(int size) {
        arr = new int[size];
        end = 0;
    }

    private void resize() {
        if (end == arr.length) {
            arr = Arrays.copyOf(arr, arr.length * 2);
        }
    }

    /**
     * @return top of the stack
     */
    public int pop() {
        return arr[--end];
    }

    /**
     * @param value push value to the stack
     */
    public void push(int value) {
        arr[end++] = value;
        resize();
    }

    /**
     * @return count of elements in the stack
     */
    public int count() {
        return end;
    }
}
