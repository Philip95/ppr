import java.util.Arrays;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

public class MergeSortParallel extends RecursiveAction {
    private final int high;
    private final int[] array;
    private final int low;

    public static void main(String[] args) {
        int[] array = numbers();
        long start = System.nanoTime();

        ForkJoinPool pool = new ForkJoinPool();
        pool.submit(new MergeSortParallel(array, 0, array.length)).join();
        System.out.println("\n " + (System.nanoTime() - start) / 1000000.0 + "[msec]");

    }

    static int[] numbers() {
        int[] numbers = new int[10000000];
        for (int i = 0; i < numbers.length; i++) {
            numbers[i] = (int) (Math.random() * 10000000);
        }
        return numbers;
    }

    MergeSortParallel(int[] array, int low, int high) {
        this.array = array;
        this.low = low;
        this.high = high;
    }

    public void compute() {
        int size = high - low;
        if (size <= 8) {
            Arrays.sort(array, low, high);
        } else {
            int middle = low + (size >> 1);
            invokeAll(new MergeSortParallel(array, low, middle), new MergeSortParallel(array, middle, high));
            merge(middle);
        }
    }

    private void merge(int middle) {
        if (array[middle - 1] < array[middle]) {
            return;
        }
        int copySize = high - low;
        int copyMiddle = middle - low;
        int[] copy = new int[copySize];
        System.arraycopy(array, low, copy, 0, copy.length);
        int p = 0;
        int q = copyMiddle;
        for (int i = low; i < high; ++i) {
            if (q >= copySize || (p < copyMiddle && copy[p] < copy[q])) {
                array[i] = copy[p++];
            } else {
                array[i] = copy[q++];
            }
        }
    }
}
