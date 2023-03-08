public class Fork {

    public final int id;
    private boolean forkIsOnTheTable = true;
    private int philosopherUsingThisFork;

    public static long waitingTime = 0;

    public int getId() {
        return id;
    }

    public boolean isForkIsOnTheTable() {
        return forkIsOnTheTable;
    }

    public Fork(int id) {
        this.id = id;
    }

    public synchronized void take(int philosopher) {
        long startTime = System.nanoTime();
        long endTime = 0;
        while (!forkIsOnTheTable) {
            try {
                wait();
                endTime += System.nanoTime();
            }
            catch (InterruptedException ignored) {}
        }
        long duration = (endTime - startTime)/1000000;

        if (duration > 0) {
            waitingTime += duration;
        }

        philosopherUsingThisFork = philosopher;

        forkIsOnTheTable = false;

        System.out.println("Waiting time: " + waitingTime + " ms");

    }

    public synchronized void put(int philosopher) {

        if (!forkIsOnTheTable && philosopherUsingThisFork == philosopher) {
            forkIsOnTheTable = true;
            notify();
        }

    }

}