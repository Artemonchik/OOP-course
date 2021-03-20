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

    public boolean find(Iterable<T> list, Predicate<T> pred, int numberOfThreads)  {
        Bool wasFind = new Bool();
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
            try {
                threads[i].join();
            } catch (InterruptedException e) {
                if(wasFind.getValue()){
                    return true;
                }
                e.printStackTrace();
                throw new Error("Some thread was interrupted");
            }
        }
        return wasFind.getValue();
    }

    public boolean findParallels(Iterable<T> iter, Predicate<T> pred) {
        return StreamSupport.stream(iter.spliterator(), false).parallel().anyMatch(pred);
    }

    public static class Bool{
        boolean value;
        Bool(){
            this(false);
        }
        Bool(boolean value){
            this.value = value;
        }

        public boolean getValue() {
            return value;
        }

        public void setValue(boolean value) {
            this.value = value;
        }
    }
}
