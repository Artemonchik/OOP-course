import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import static org.junit.Assert.*;

public class PrimeNumberFinderTest {
    PrimeChecker primeChecker = new PrimeChecker();
    Finder<Integer> finder = new Finder<>();
    int defThreadNum = 6;
    @Test
    public void compareParallelEfficiency() throws IOException {
        ArrayList<Integer> arr = readLargeArray("test.txt");
        TimeTriplet test1 = runSingleThread(arr);
        assertFalse(test1.res);
        System.out.printf("Time executed for sequential execution thread is: %d ms\n", test1.endTime - test1.startTime);

        int threadsNum = 50;
        for (int i = 1; i <= threadsNum; i++) {
            TimeTriplet test = runMultiplyThreads(arr, i);
            assertFalse(test.res);
            System.out.printf("Time executed for %d threads is: %d ms\n", i, test.endTime - test.startTime);
        }

        TimeTriplet testParallels = runParallels(arr);
        assertFalse(testParallels.res);
        System.out.printf("Time executed for parallels execution is: %d ms\n", testParallels.endTime - testParallels.startTime);
    }

    @Test
    public void simpleTests() {

        ArrayList<Integer> list1 = listFromParams(2, 3, 5, 7, 11, 13, 17, 23);
        assertFalse(finder.find(list1, primeChecker));
        assertFalse(finder.find(list1, primeChecker, defThreadNum));
        assertFalse(finder.findParallels(list1, primeChecker));

        ArrayList<Integer> list2 = listFromParams( 2, 3, 5, 7, 11, 14, 17, 23, 27);
        assertTrue(finder.find(list2, primeChecker));
        assertTrue(finder.find(list2, primeChecker, defThreadNum));
        assertTrue(finder.findParallels(list2, primeChecker));

        ArrayList<Integer> list3 = listFromParams( 2, 3, 5, 7, 11, 11, 17, 23, 27);
        assertTrue(finder.find(list3, primeChecker));
        assertTrue(finder.find(list3, primeChecker, defThreadNum));
        assertTrue(finder.findParallels(list3, primeChecker));
    }

    private ArrayList<Integer> readLargeArray(String fileName) throws IOException {
        File file = new File(fileName);
        DataInputStream in = new DataInputStream(new FileInputStream(file));
        int fsize = (int) file.length();
        ArrayList<Integer> result = new ArrayList<>();
        for (int i = 0; i < fsize; i += 4) {
            int idx = i / 4;
            int elem = in.readInt();
            result.add(elem);
        }
        return result;
    }

    private TimeTriplet runSingleThread(ArrayList<Integer> array) {
        long timeStart = System.currentTimeMillis();
        boolean result = finder.find(array, primeChecker);
        long timeEnd = System.currentTimeMillis();

        return new TimeTriplet(timeStart, timeEnd, result);
    }

    private TimeTriplet runMultiplyThreads(ArrayList<Integer> array, int threadNum) {
        long timeStart = System.currentTimeMillis();
        boolean result = finder.find(array, primeChecker, threadNum);
        long timeEnd = System.currentTimeMillis();

        return new TimeTriplet(timeStart, timeEnd, result);
    }

    private TimeTriplet runParallels(ArrayList<Integer> array) {
        long timeStart = System.currentTimeMillis();
        boolean result = finder.findParallels(array, primeChecker);
        long timeEnd = System.currentTimeMillis();

        return new TimeTriplet(timeStart, timeEnd, result);
    }

    private ArrayList<Integer> listFromParams(int... a) {
        ArrayList<Integer> result = new ArrayList<>();
        for (int i : a)
            result.add(i);
        return result;
    }

    private static class TimeTriplet {
        private final long startTime;
        private final long endTime;
        private final boolean res;

        private TimeTriplet(long startTime, long endTime, boolean res) {
            this.startTime = startTime;
            this.endTime = endTime;
            this.res = res;
        }
    }
}
