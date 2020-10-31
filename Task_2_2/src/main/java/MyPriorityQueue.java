import java.util.*;
import java.util.stream.Stream;

import javafx.util.Pair;

public class MyPriorityQueue<KeyT extends Comparable<KeyT>, ValueT>
        implements Iterable<Pair<KeyT, ValueT>>, Iterator<Pair<KeyT, ValueT>> {

    private final PriorityQueue<Pair<KeyT, ValueT>> queue = new PriorityQueue<>(10, Comparator.comparing(Pair::getKey));

    public void insert(KeyT key, ValueT value) {
        if (key == null || value == null) {
            throw new NullPointerException("key or value are null");
        }
        queue.add(new Pair<>(key, value));
    }

    public Pair<KeyT, ValueT> extractMin() throws ArrayStoreException {
        if (queue.size() == 0) {
            throw new ArrayStoreException("Storage is empty");
        }
        return queue.poll();
    }

    @Override
    public boolean hasNext() {
        return queue.size() > 0;
    }

    @Override
    public Pair<KeyT, ValueT> next() {
        if (!hasNext()) {
            throw new NoSuchElementException("Stack is empty");
        }
        return extractMin();
    }

    @Override
    public Iterator<Pair<KeyT, ValueT>> iterator() {
        return this;
    }

    public Stream<Pair<KeyT, ValueT>> stream() {
        return queue.stream();
    }
}
