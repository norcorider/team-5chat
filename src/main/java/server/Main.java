package server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    private static final int PORT = 60000;
    private static List<PrintWriter> pts = new ArrayList<>();


    public static void main(String[] args) {
        try (ServerSocket server = new ServerSocket(PORT)) {
            while (true) {
                Socket client = server.accept();
                new Thread(() -> clientLoop(client)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void clientLoop(Socket client) {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()))) {
            System.out.println("Установлено соединение с " + client.getInetAddress().toString());
            pts.add(new PrintWriter(client.getOutputStream(), true));
            String a;
            do {

                a = in.readLine();
                sendToAll(a);
                System.out.println(a);
            } while (a != null);

        } catch (SocketException t) {
            System.out.println("Потеряно соединение");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void sendToAll(String a) {
        for (PrintWriter pw : pts) {
            pw.println(a);
        }
    }
}