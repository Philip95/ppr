public class NumberGenerator {
    public static int[] numbers() {
        int[] numbers = new int[10000000];
        for (int i = 0; i < numbers.length; i++) {
            numbers[i] = (int) (Math.random() * 10000000);
        }
        return numbers;
    }
}
