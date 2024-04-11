import java.io.*;
import java.net.*;

public class SimpleHttpServer {
    public static void main(String[] args) {
        int port = 8080; // Порт, на якому запускається сервер

        try {
            // Створення серверного сокету
            ServerSocket serverSocket = new ServerSocket(port);
            System.out.println("HTTP Server started on port " + port);

            // Безкінечний цикл для прийому клієнтських запитів
            while (true) {
                // Приймання клієнтського сокету
                Socket clientSocket = serverSocket.accept();

                // Створення обробника запитів для кожного клієнтського сокету
                Thread thread = new Thread(() -> {
                    try {
                        // Отримання введення та виведення з клієнтського сокету
                        BufferedReader inputReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                        BufferedWriter outputWriter = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));

                        // Отримання HTTP-запиту від клієнта
                        String requestLine = inputReader.readLine();
                        System.out.println("Received request: " + requestLine);

                        // Відправлення HTTP-відповіді клієнту
                        String response = "HTTP/1.1 200 OK\r\nContent-Type: text/plain\r\n\r\nHello from Java HTTP Server";
                        outputWriter.write(response);
                        outputWriter.flush();

                        // Закриття з'єднання з клієнтом
                        clientSocket.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });

                // Запуск обробника запитів у окремому потоці
                thread.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
