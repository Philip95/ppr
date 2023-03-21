public class MergeSort {
    public static void main(String[] args) {
        MergeSort ob = new MergeSort();
        long start = System.nanoTime();
        ob.sort(NumberGenerator.numbers(), 0, NumberGenerator.numbers().length - 1);
        System.out.println("\n "+ (System.nanoTime() - start) / 1000000.0 + " ms");
    }

    private void merge(int[] numbers, int l, int m, int r) {
        int n1 = m - l + 1;
        int n2 = r - m;

        int[] left = new int[n1];
        int[] right = new int[n2];

        System.arraycopy(numbers, l, left, 0, n1);

        for (int j = 0; j < n2; ++j)
            right[j] = numbers[m + 1 + j];

        int i = 0, j = 0;

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

        while (i < n1) {
            numbers[k] = left[i];
            i++;
            k++;
        }

        while (j < n2) {
            numbers[k] = right[j];
            j++;
            k++;
        }
    }

    private void sort(int[] arr, int l, int r) {
        if (l < r) {
            int m = l + (r - l) / 2;

            sort(arr, l, m);
            sort(arr, m + 1, r);

            merge(arr, l, m, r);
        }
    }
}
