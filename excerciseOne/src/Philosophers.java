

/*
    * Runnable is preferred over Thread because it is more flexible.
    * Can extend other class.
    * can reuse the same Runnable instance for multiple threads.
    * could also be used like Executor
    * Runnable provide composition over inheritance.
 */
public class Philosophers implements Runnable {

    private String leftFork;
    private String rightFork;

    public Philosophers(String leftFork, String rightFork) {
        this.leftFork = leftFork;
        this.rightFork = rightFork;
    }

    @Override
    public String toString() {
        return "Philosophers{" +
                "leftFork='" + leftFork + '\'' +
                ", rightFork='" + rightFork + '\'' +
                '}';
    }

    public String getLeftFork() {
        return leftFork;
    }

    public String getRightFork() {
        return rightFork;
    }

    @Override
    public void run() {
        System.out.println(this);
    }

}