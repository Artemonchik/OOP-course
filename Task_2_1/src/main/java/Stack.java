import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.concurrent.TransferQueue;

public class Stack<Type> implements Iterator<Type> {
    private Type[] arr;
    private int end;

    /**
     * Create stack with buffer with 10 elem size
     */
    @SuppressWarnings("unchecked")
    Stack() {
        arr = (Type[])new Object[10];
        end = 0;
    }

    /**
     * @param size initial value of stack buffer
     */
    @SuppressWarnings("unchecked")
    Stack(int size) {
        if(size < 0){
            throw new NegativeArraySizeException("You cannot create array with negative size");
        }
        arr = (Type[]) new Object[size];
        end = 0;
    }

    private void resize() {
        if (end >= arr.length - 1) {
            arr = Arrays.copyOf(arr, arr.length * 2);
        }
    }

    /**
     * @return top of the stack
     */
    public Type pop() {
        return arr[--end];
    }

    /**
     * @param value push value to the stack
     */
    public void push(Type value) {
        resize();
        arr[end++] = value;
    }

    /**
     * @return count of elements in the stack
     */
    public int count() {
        return end;
    }

    @Override
    public boolean hasNext() {
        return end > 0;
    }

    @Override
    public Type next() {
        if(!hasNext()){
            throw new NoSuchElementException("Stack is empty");
        }
        return pop();
    }
}
