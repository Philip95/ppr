import java.util.*;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);

    public static int numberOfPhilosophers;

    public static void main(String[] args) {
        long startTime = System.nanoTime();
        long endTime = 0;
        Map<String, Integer> map = readInput();
        int n = map.get("n");
        int thinkingTime = map.get("thinkingTime");
        int eatingTime = map.get("eatingTime");

        numberOfPhilosophers = n;


        Philosopher[] philosophers = createPhilosophers(n, thinkingTime, eatingTime);

        for (Philosopher philosopher : philosophers) {
            philosopher.start();
        }

        String exitCode = scanner.next();
        if (exitCode.equals("e")) {
            for (Philosopher philosopher : philosophers) {
                philosopher.stopThread();
                try {
                    philosopher.join();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                endTime = System.nanoTime();
            }

            long runtime = (endTime - startTime)/1000000;
            long waitingTime = Fork.waitingTime;

            System.out.println("Runtime: " + runtime + " ms");
            System.out.println("Waiting time: " + waitingTime + " ms");
        }
        scanner.close();
    }

    public static Philosopher[] createPhilosophers(int n, int thinkingTime, int eatingTime) {
        Fork[] forks = new Fork[n];

        for (int i = 0; i < n; i++) {
            forks[i] = new Fork(i);
        }

        Philosopher[] philosophers = new Philosopher[n];

        for (int i = 0; i < n; i++) {
            Fork leftFork = forks[i];
            Fork rightFork = forks[(i + 1) % n];

            philosophers[i] = new Philosopher(i, leftFork, rightFork, thinkingTime, eatingTime);
        }
        return philosophers;
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

    public static int getNumberOfPhilosophers() {
        return numberOfPhilosophers;
    }
}
