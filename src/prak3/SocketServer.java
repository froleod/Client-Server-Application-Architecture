package prak3;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.*;

/**
 * Серверная программа, которая принимает сообщения от клиентов и раз в 5 секунд
 * рассылает накопленные сообщения всем подключенным клиентам.
 */
public class SocketServer {

    private static final int PORT = 59898;
    private static final ConcurrentLinkedQueue<String> messageQueue = new ConcurrentLinkedQueue<>();
    private static final CopyOnWriteArrayList<PrintWriter> clientWriters = new CopyOnWriteArrayList<>();

    /**
     * Запускает сервер. Когда клиент подключается, сервер создает новый поток для обслуживания
     * и возвращается к прослушиванию. Приложение ограничивает количество потоков через
     * пул потоков.
     *
     * @param args аргументы командной строки (не используются)
     * @throws Exception если произошла ошибка ввода-вывода при запуске сервера
     */
    public static void main(String[] args) throws Exception {
        ExecutorService pool = Executors.newFixedThreadPool(20);

        try (var listener = new ServerSocket(PORT)) {
            System.out.println("Сервер запущен...");
            new Thread(new MessageDispatcher()).start();
            while (true) {
                pool.execute(new MessagesHandler(listener.accept()));
            }
        }
    }

    /**
     * Обработчик подключений клиентов. Отвечает за прием сообщений от клиента и добавление их в очередь сообщений.
     */
    private static class MessagesHandler implements Runnable {
        private Socket socket;

        MessagesHandler(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            System.out.println("Подключение: " + socket);
            try (var in = new Scanner(socket.getInputStream());
                 var out = new PrintWriter(socket.getOutputStream(), true)) {
                clientWriters.add(out);
                while (in.hasNextLine()) {
                    messageQueue.add(in.nextLine());
                }
            } catch (IOException e) {
                System.out.println("Ошибка: " + e.getMessage());
            } finally {
                try {
                    socket.close();
                } catch (IOException e) {
                    System.err.println("Ошибка при закрытии сокета: " + e.getMessage());
                }
                System.out.println("Closed: " + socket);
            }
        }
    }

    /**
     * Механизм распределения накопленных сообщений. Раз в 5 секунд рассылает накопленные сообщения
     * всем подключенным клиентам.
     */
    private static class MessageDispatcher implements Runnable {
        @Override
        public void run() {
            while (true) {
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    return;
                }

                List<String> messagesToSend = new ArrayList<>();
                while (!messageQueue.isEmpty()) {
                    messagesToSend.add(messageQueue.poll());
                }

                if (!messagesToSend.isEmpty()) {
                    String combinedMessage = String.join(", ", messagesToSend);
                    for (PrintWriter writer : clientWriters) {
                        writer.println(combinedMessage);
                    }
                }
            }
        }
    }
}

