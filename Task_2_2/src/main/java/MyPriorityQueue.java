import java.util.*;

import com.sun.jdi.Value;
import javafx.util.Pair;

public class MyPriorityQueue<KeyT extends Comparable<KeyT>, ValueT>
        implements Iterable<Pair<KeyT, ValueT>>, Iterator<Pair<KeyT, ValueT>> {

    ArrayList<Pair<KeyT, ValueT>> array = new ArrayList<>(5);

    /**
     * This method sift up element in heap: it compares this element with the parent node and if elem is greater then
     * method swaps these nodes
     *
     * @param idx   index of node we want to sift up
     */
    private void siftUp(int idx) {
        while (array.get(idx).getKey().compareTo(array.get((idx - 1) / 2).getKey()) > 0 && idx > 0) {
            Pair<KeyT, ValueT> t = array.get(idx);
            array.set(idx, array.get((idx - 1) / 2));
            array.set((idx - 1) / 2, t);
            idx = (idx - 1) / 2;
        }
    }

    /**
     * compares node with his two children, chooses the max and swap with it. Repeats this action before end of heap
     *
     * @param idx index of element we should sift down
     */
    private void siftDown(int idx) {
        int n = array.size();
        int largest = idx;
        int left = idx * 2 + 1;
        int right = idx * 2 + 2;
        if (left < n && array.get(largest).getKey().compareTo(array.get(left).getKey()) < 0) {
            largest = left;
        }
        if (right < n && array.get(largest).getKey().compareTo(array.get(right).getKey()) < 0) {
            largest = right;
        }
        if (largest != idx) {
            Pair<KeyT, ValueT> t = array.get(largest);
            array.set(largest, array.get(idx));
            array.set(idx, t);
            siftDown(largest);
        }
    }

    /**
     * Pops the toppest element of the heap preserving the heap invariant
     *
     * @return the largrst element of the heap
     */
    private Pair<KeyT, ValueT> popElement() {
        int last = array.size() - 1;
        Pair<KeyT, ValueT> returnValue = array.get(0);
        array.set(0, array.get(last));
        array.remove(array.size() - 1);
        siftDown(0);
        return returnValue;
    }

    /**
     * @param key key of the pair
     * @param value value of the pair
     * @throws NullPointerException if key or value are null
     */
    public void insert(KeyT key, ValueT value) throws NullPointerException{
        if(key == null || value == null){
            throw new NullPointerException("key or value arguments are null");
        }
        Pair<KeyT, ValueT> pair = new Pair<>(key, value);
        array.add(pair);
        siftUp(array.size() - 1);
    }

    /**
     * @return the maximum value of the priority queue
     * @throws ArrayStoreException if priority queue is empty
     */
    public Pair<KeyT, ValueT> extractMaximum() throws ArrayStoreException{
        if(!hasNext()){
            throw new ArrayStoreException("Stack is empty");
        }
        return popElement();
    }

    @Override
    public Iterator<Pair<KeyT, ValueT>> iterator() {
        return array.iterator();
    }

    @Override
    public boolean hasNext() {
        return array.size() > 0;
    }

    @Override
    public Pair<KeyT, ValueT> next() {
        return extractMaximum();
    }
}
