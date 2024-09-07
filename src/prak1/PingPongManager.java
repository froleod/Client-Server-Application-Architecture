package prak1;

public class PingPongManager {
    private final Object lock = new Object(); // для синхронизации потоков
    private boolean pingTurn = true; // флаг очередности потоков

    public void printPing() {
        synchronized (lock) {
            while (!pingTurn) {
                try {
                    lock.wait();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
            System.out.print("Ping ");
            pingTurn = false;
            lock.notify();
        }
    }

    public void printPong() {
        synchronized (lock) {
            while (pingTurn) {
                try {
                    lock.wait();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
            System.out.print("Pong ");
            pingTurn = true;
            lock.notify();
        }
    }
}

