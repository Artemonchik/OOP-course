import java.util.Iterator;
import java.util.function.Predicate;

public class FinderThread<T> extends Thread {
    Iterator<T> iter;
    Predicate<T> comp;
    int threadNumbers;
    boolean wasFound;

    FinderThread(Iterator<T> iter, Predicate<T> comp, int threadNumbers, boolean wasFound) {
        this.iter = new Iterator<T>() {
            int idx = 0;

            @Override
            public boolean hasNext() {
                while (iter.hasNext()) {
                    if (idx % threadNumbers == 0) {
                        return true;
                    }
                    iter.next();
                    idx++;
                }
                return false;
            }

            @Override
            public T next() {
                if (this.hasNext()) {
                    return this.next();
                }
                return null;
            }
        };
        this.comp = comp;
        this.threadNumbers = threadNumbers;
        this.wasFound = wasFound;
    }

    private boolean find() {
        while (iter.hasNext() && !wasFound) {
            T elem = iter.next();
            if (comp.test(elem)) {
                return true;
            }
        }
        return false;
    }

    public void run() {
        boolean result = find();
        if(result){
            wasFound = true;
        }
    }
}
