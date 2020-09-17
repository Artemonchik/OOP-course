import org.junit.*;

import javax.print.DocFlavor;

import static org.junit.jupiter.api.Assertions.*;

public class HeapSortTests {
    @Test
    public void simpleTests(){
        int[] arr1 = {4,5,1,0, -5};
        int[] sortedArr1 = {-5,0,1,4,5};
        HeapSort.heapSort(arr1, arr1.length);
        assertArrayEquals(sortedArr1, arr1);

        int[] arr2 = {324,13,213,67,0,0,0,-123,-345,-34};
        int[] sortedArr2 = {-345,-123,-34,0,0,0,13,67,213,324};
        HeapSort.heapSort(arr2, arr2.length);
        assertArrayEquals(sortedArr2, arr2);
    }

    @Test
    public void emptyArrayTest(){
        int[] emptyArray = {};
        int[] emptyArraySorted = {};
        HeapSort.heapSort(emptyArray,0);
        assertArrayEquals(emptyArray, emptyArraySorted);
    }

    @Test
    public void testWithLimitIntegerValues(){
        int[] arr = {3,1,2, Integer.MIN_VALUE, Integer.MAX_VALUE, Integer.MIN_VALUE, Integer.MAX_VALUE};
        int[] sortedArr = {Integer.MIN_VALUE,Integer.MIN_VALUE,1,2,3, Integer.MAX_VALUE,Integer.MAX_VALUE};
        HeapSort.heapSort(arr, arr.length);
        assertArrayEquals(arr, sortedArr);
    }
}
