package su.jet.team05.chat.client;

import su.jet.team05.chat.exception.messageException;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.util.Scanner;

import static java.lang.Thread.sleep;

public class Client {
    private static final String IP = "localhost";
    private static final int PORT = 60000;

    private Client() {
    }

    public static void main(String[] args) {
        try {
            clientWork();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static PrintWriter setClientWriter(Socket client) {
        try {
            return new PrintWriter(client.getOutputStream(), true);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static BufferedReader setClientReader(Socket client) {
        try {
            return new BufferedReader(new InputStreamReader(client.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static void clientWork() throws IOException {
        Socket client = null;
        client = clientRecconect(client);

        PrintWriter printWriter = setClientWriter(client);
        BufferedReader reader = setClientReader(client);

        Scanner scanner = new Scanner(System.in);
        Thread readThread = new Thread(() -> {
            while (true) {
                try {
                    System.out.println(reader.readLine());
                } catch (IOException e) {
                    System.out.println("Сервер временно недоступен!");
                    break;
                }
            }
        });

        readThread.setDaemon(true);
        readThread.start();
        int state = 0;
        do {
            try {
                state = Message.parseMessage(scanner.nextLine(), printWriter);
            } catch (messageException e) {
                state = -1;
                System.out.println(e.getMessage());
            }
        } while (state != 0);
    }

    private static Socket clientRecconect(Socket client) throws IOException {
        while (client == null || !client.isConnected()) {
            try {
                client = new Socket(IP, PORT);
                System.out.println("Соедимение с сервером успешно!");
                System.out.println("Возможные команды:");
                System.out.println("Отправить сообщение: /snd");
                System.out.println("Вывести историю: /hist");
                System.out.println("Изменить имя пользователя: /chid <NickName>");
                System.out.println("Выход из программы: /exit");
            } catch (SocketException s) {
                System.out.println("Сервер временно недоступен!");
            }

            try {
                sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return client;
    }
}
