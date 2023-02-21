import java.util.*;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        Map<String, Integer> map = readInput();
        int n = map.get("n");
        int thinkingTime = map.get("thinkingTime");
        int eatingTime = map.get("eatingTime");

        scanner.close();

        List<Philosophers> philosophers = new ArrayList<>();
        List<Thread> threads = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            philosophers.add(new Philosophers("Fork " + i, "Fork " + ((i + 1) % n)));
        }

        for (int i = 0; i < n; i++) {
            threads.add(new Thread(philosophers.get(i)));
        }

        for (int i = 0; i < n; i++) {
            threads.get(i).start();
        }
    }

    private static Map<String, Integer> readInput() {
        Map<String, Integer> map = new HashMap<>();

        map.put("n", callScanner("Enter the number of the philosophers at the table:"));
        map.put("thinkingTime", callScanner("Enter the maximal thinking time of philosophers:"));
        map.put("eatingTime", callScanner("Enter the maximal eating time of philosophers:"));

        return map;
    }

    private static int callScanner(String msg) {
        System.out.println(msg);
        return scanner.nextInt();
    }

}
