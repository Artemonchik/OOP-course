import java.util.ArrayDeque;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class Storage<T> {
    int capacity;
    Queue<T> queue;
    public Storage(int capacity){
        this.capacity = capacity;
        this.queue = new ArrayDeque<>(capacity);
    }
    synchronized public T popItem() throws InterruptedException {
        return queue.remove();
    }
    synchronized public void pushItem(T order) throws InterruptedException {
        if(getFreeSpace() != 0){
            queue.add(order);
        }else{
            throw new ArrayStoreException("Storage is full");
        }
    }
    public boolean isEmpty(){
        return queue.isEmpty();
    }
    public int size(){
        return queue.size();
    }
    public int getFreeSpace(){
        return capacity - queue.size();
    }
}
