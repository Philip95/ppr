import java.util.Random;

public class Philosopher extends Thread {

    private final int id;
    private boolean exit;
    private Fork leftFork;
    private Fork rightFork;

    private int thinkingTime;
    private int eatingTime;
    Random random = new Random();

    public Philosopher(int id, Fork leftFork, Fork rightFork, int thinkingTime, int eatingTime) {
        this.id = id;
        this.leftFork = leftFork;
        this.rightFork = rightFork;
        this.thinkingTime = thinkingTime;
        this.eatingTime = eatingTime;
        this.exit = false;
    }

    public void stopThread() {
        exit = true;
    }

    @Override
    public void run() {

        while (!exit) {
            try {
                int randomThinkingTime = random.nextInt(this.thinkingTime);
                Thread.sleep(randomThinkingTime);

                System.out.println("Philosoph " + this.id + " finished thinking");

                if (id % 2 == 0) {
                    takeForksInEvenOrder();
                } else {
                    takeForksInOddOrder();
                }

                eatingTime();

                putDownForksInArbitraryOrder();

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }
        }
    }

    private void takeForksInOddOrder() {
        System.out.println("Philosoph " + this.id + " tries to pick up left fork " + leftFork.getId() + ", available " + leftFork.isForkIsOnTheTable());
        leftFork.take(id);
        System.out.println("Philosoph " + this.id + " picked up left fork " + leftFork.getId() + ", available " + leftFork.isForkIsOnTheTable());

        System.out.println("Philosoph " + this.id + " tries to pick up right fork " + rightFork.getId() + ", available " + rightFork.isForkIsOnTheTable());
        rightFork.take((id + 1) % Main.getNumberOfPhilosophers());
        System.out.println("Philosoph " + this.id + " picked up right fork " + rightFork.getId() + ", available " + rightFork.isForkIsOnTheTable());
    }

    private void takeForksInEvenOrder() {
        System.out.println("Philosoph " + this.id + " tries to pick up right fork " + rightFork.getId() + ", available " + rightFork.isForkIsOnTheTable());
        rightFork.take((id + 1) % Main.getNumberOfPhilosophers());
        System.out.println("Philosoph " + this.id + " picked up right fork " + rightFork.getId() + ", available " + rightFork.isForkIsOnTheTable());

        System.out.println("Philosoph " + this.id + " tries to pick up left fork " + leftFork.getId() + ", available " + leftFork.isForkIsOnTheTable());
        leftFork.take(id);
        System.out.println("Philosoph " + this.id + " picked up left fork " + leftFork.getId() + ", available " + leftFork.isForkIsOnTheTable());
    }

    private void eatingTime() {
        int randomEatingTime = random.nextInt(this.eatingTime);
        try {
            Thread.sleep(randomEatingTime);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Philosoph " + this.id + " finished eating");
    }

    public void putDownForksInArbitraryOrder() {
        int randomPlace = random.nextInt(2);

        if (randomPlace == 0) {
            leftFork.put(id);
            System.out.println("Put down left fork " +  leftFork.getId());
            rightFork.put((id + 1) % Main.getNumberOfPhilosophers());
            System.out.println("Put down right fork " + rightFork.getId());
        } else {
            rightFork.put((id + 1) % Main.getNumberOfPhilosophers());
            System.out.println("Put down right fork " + rightFork.getId());
            leftFork.put(id);
            System.out.println("Put down left fork " + leftFork.getId());
        }
    }

}