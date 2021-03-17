import java.util.Arrays;
import java.util.Iterator;
import java.util.function.Predicate;
import java.util.stream.StreamSupport;

public class Finder<T> {
    public boolean find(Iterable<T> iter, Predicate<T> pred) {
        for (T elem : iter) {
            if (pred.test(elem)) {
                return true;
            }
        }
        return false;
    }

    public boolean find(Iterable<T> list, Predicate<T> pred, int numberOfThreads) throws InterruptedException {
        boolean wasFind = false;
        Thread[] threads = new Thread[numberOfThreads];

        for (int i = 0; i < numberOfThreads; i++) {
            Iterator<T> iter = list.iterator();
            for (int j = 0; j < i; j++) {
                iter.next();
            }
            threads[i] = new FinderThread<T>(iter, pred, numberOfThreads, wasFind);
        }
        for (int i = 0; i < numberOfThreads; i++) {
            threads[i].start();
        }
        for (int i = 0; i < numberOfThreads; i++) {
            threads[i].join();
        }
        return wasFind;
    }

    public boolean findParallels(Iterable<T> iter, Predicate<T> pred) {
        return StreamSupport.stream(iter.spliterator(), true).anyMatch(pred);
    }
}
