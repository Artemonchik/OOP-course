import java.util.Arrays;

public class HeapSort {
    /**
     * This method sift up element in heap: it compares this element with the parent node and if elem is greater then
     * method swaps these nodes
     * @param array heap
     * @param idx index of node we want to sift up
     */
    private static void siftUp(int[] array, int idx){
        while (array[idx] > array[(idx-1)/2] && idx > 0){
            int t = array[idx];
            array[idx] = array[(idx-1)/2];
            array[(idx-1)/2] = t;
            idx = (idx-1)/2;
        }
    }

    /**
     * compares node with his two children, chooses the max and swap with it. Repeats this action before end of heap
     * @param array heap
     * @param n len of heap
     * @param idx index of element we should sift down
     */
    private static void siftDown(int[] array, int n, int idx){
        int largest = idx;
        int left = idx * 2 + 1;
        int right = idx * 2 + 2;
        if(left < n && array[largest] < array[left]){
            largest = left;
        }
        if(right < n && array[largest] < array[right]){
            largest = right;
        }
        if(largest != idx){
            int t = array[largest];
            array[largest] = array[idx];
            array[idx] = t;
            siftDown(array, n, largest);
        }
    }

    /**
     * Pops the toppest element of the heap preserving the heap invariant
     * @param array heap
     * @param len len of heap
     * @return the largrst element of the heap
     */
    private static int popElement(int[] array, int len){
        int last = len - 1;
        int returnValue = array[0];
        array[0] = array[last];
        array[last] = returnValue;
        siftDown(array, len - 1, 0);
        return returnValue;
    }

    /**
     * Heap sort of array
     * @param array array to sort
     * @param len its length
     */
    public static void heapSort(int[] array, int len){
        for(int i = 0; i < len; i++){
            siftUp(array, i);
        }

        for(int i = len - 1; i >= 0; i--){
           array[i] = popElement(array, i + 1);
        }
    }
}
