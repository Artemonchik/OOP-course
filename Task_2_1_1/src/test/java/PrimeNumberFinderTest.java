import java.util.Random;

import org.junit.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PrimeNumberFinderTest {
    @Test
    public void compareParallelEfficiency() throws InterruptedException {
        final int[] testArray = generateArray(100000000, 1, 3000000);
        long oneThreadTimeStart = System.currentTimeMillis();
        assertFalse(NotPrimeNumberFinder.findNotPrime(testArray));
        long oneThreadTimeEnd = System.currentTimeMillis();
        System.out.printf("Time executed for sequential execution thread is: %d ms\n", oneThreadTimeEnd - oneThreadTimeStart);

        int numberOfThreads = 12;
        for (int i = 1; i <= numberOfThreads; i++) {
            long multiplyThreadTimeStart = System.currentTimeMillis();
            assertFalse(NotPrimeNumberFinder.findPrimeNumberWithThreads(testArray, i));
            long multiplyThreadTimeEnd = System.currentTimeMillis();
            System.out.printf("Time executed for %d threads is: %d ms\n", i, multiplyThreadTimeEnd - multiplyThreadTimeStart);
        }

        long parallelsTimeStart = System.currentTimeMillis();
        assertFalse(NotPrimeNumberFinder.findPrimeNumberParallels(testArray));
        long parallelsTimeEnd = System.currentTimeMillis();
        System.out.printf("Time executed for parallels is: %d ms\n", parallelsTimeEnd - parallelsTimeStart);
    }

    @Test
    public void testFindPrimeNumber() {
        int n = 50;
        int low = 0;
        int high = 3500;
        int numOfIterations = 100;
        Random r = new Random();
        for (int i = 0; i < numOfIterations; i++) {
            int[] arr = generateArray(n, low, high);
            arr[r.nextInt(n)] = 3 * 7;
            assertTrue(NotPrimeNumberFinder.findNotPrime(arr));
        }
    }

    private int[] generateArray(int n, int low, int high) {
        int[] result = new int[n];
        Random r = new Random();
        for (int i = 0; i < n; i++) {
            int num = r.nextInt(high - low) + low;
            if (!NotPrimeNumberFinder.isPrime(num)) {
                result[i] = num;
            } else {
                i--;
            }
        }
        return result;
    }

}
