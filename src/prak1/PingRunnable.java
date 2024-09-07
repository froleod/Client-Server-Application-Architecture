package prak1;

public class PingRunnable implements Runnable {
    private final PingPongManager manager;

    public PingRunnable(PingPongManager manager) {
        this.manager = manager;
    }

    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            manager.printPing();
        }
    }
}

