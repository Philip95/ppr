public class MergeSort {
    public static void main(String[] args) {
        //System.out.println("Given Array");
        //printArray(MergeSortParallel.numbers());

        MergeSort ob = new MergeSort();
        long start = System.nanoTime();
        ob.sort(MergeSortParallel.numbers(), 0, MergeSortParallel.numbers().length - 1);
        System.out.println("\n "+ (System.nanoTime() - start) / 1000000.0 + "[msec]");

        //System.out.println("\nSorted array");
        //printArray(numbers);
    }

    void merge(int numbers[], int l, int m, int r) {
        // Find sizes of two subarrays to be merged
        int n1 = m - l + 1;
        int n2 = r - m;

        /* Create temp arrays */
        int[] left = new int[n1];
        int[] right = new int[n2];

        /*Copy data to temp arrays*/
        System.arraycopy(numbers, l, left, 0, n1);

        for (int j = 0; j < n2; ++j)
            right[j] = numbers[m + 1 + j];

        /* Merge the temp arrays */

        // Initial indexes of first and second subarrays
        int i = 0, j = 0;

        // Initial index of merged subarray array
        int k = l;
        while (i < n1 && j < n2) {
            if (left[i] <= right[j]) {
                numbers[k] = left[i];
                i++;
            }
            else {
                numbers[k] = right[j];
                j++;
            }
            k++;
        }

        /* Copy remaining elements of Left[] if any */
        while (i < n1) {
            numbers[k] = left[i];
            i++;
            k++;
        }

        /* Copy remaining elements of Right[] if any */
        while (j < n2) {
            numbers[k] = right[j];
            j++;
            k++;
        }
    }

    void sort(int arr[], int l, int r) {
        if (l < r) {
            // Find the middle point
            int m = l + (r - l) / 2;

            // Sort first and second halves
            sort(arr, l, m);
            sort(arr, m + 1, r);

            // Merge the sorted halves
            merge(arr, l, m, r);
        }
    }

    static void printArray(int arr[]) {
        for (int j : arr) System.out.print(j + " ");
    }
}
