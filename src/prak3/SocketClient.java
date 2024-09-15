package prak3;

import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

/**
 * Клиентская программа, которая подключается к серверу, отправляет ему сообщения
 * и отображает сообщения, рассылка которых производится сервером.
 */
public class SocketClient {

    private static final int PORT = 59898;

    /**
     * Запускает клиентское приложение. К клиенту подключается сервер
     * и обеспечивает возможность отправки сообщений на сервер.
     *
     * @param args IP-адрес сервера
     * @throws Exception при ошибках ввода-вывода
     */
    public static void main(String[] args) throws Exception {
        if (args.length != 1) {
            System.err.println("Передайте IP-адрес сервера в качестве единственного аргумента командной строки");
            return;
        }
        try (var socket = new Socket(args[0], PORT)) {
            System.out.println("Введите строки текста, затем Ctrl + D или Ctrl + C, чтобы выйти");
            var consoleScanner = new Scanner(System.in);
            var serverIn = new Scanner(socket.getInputStream());
            var serverOut = new PrintWriter(socket.getOutputStream(), true);

            new Thread(new ServerListener(serverIn)).start();

            while (consoleScanner.hasNextLine()) {
                serverOut.println(consoleScanner.nextLine());
            }
        }
    }

    /**
     * Вспомогательный класс для прослушивания и отображения сообщений от сервера
     */
    private static class ServerListener implements Runnable {
        private Scanner serverIn;

        ServerListener(Scanner serverIn) {
            this.serverIn = serverIn;
        }

        @Override
        public void run() {
            while (serverIn.hasNextLine()) {
                System.out.println("Сообщение от сервера: " + serverIn.nextLine());
            }
        }
    }
}
