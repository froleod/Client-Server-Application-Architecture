package prak1;

public class PingPongMain {
    public static void main(String[] args) {
        PingPongManager manager = new PingPongManager();
        Thread pingThread = new Thread(new PingRunnable(manager));
        Thread pongThread = new Thread(new PongRunnable(manager));

        pingThread.start();
        pongThread.start();
    }
}
