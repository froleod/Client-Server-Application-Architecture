package prak1;

public class PongRunnable implements Runnable {
    private final PingPongManager manager;

    public PongRunnable(PingPongManager manager) {
        this.manager = manager;
    }

    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            manager.printPong();
        }
    }
}

