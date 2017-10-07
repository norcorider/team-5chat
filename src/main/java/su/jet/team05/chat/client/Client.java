package su.jet.team05.chat.client;

import su.jet.team05.chat.exception.messageException;

import java.io.*;
import java.net.Socket;
import java.util.Date;
import java.util.Scanner;

public class Client {
    private static final String IP = "localhost";
    private static final int PORT = 60000;

    public static void main(String[] args) {
        try {
            Socket client = new Socket(IP, PORT);
            PrintWriter out = new PrintWriter(client.getOutputStream(), true);
            BufferedReader reader = new BufferedReader(new InputStreamReader(client.getInputStream()));
            Scanner in = new Scanner(System.in);
            Thread t = new Thread(() -> {
                try {
                    while (true) {
                        System.out.println(reader.readLine());
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            t.setDaemon(true);
            t.start();
            int state = 0;
            do {
                try {
                    state = Message.parseMessage(in.nextLine(), out);
                } catch (messageException e) {
                    state = -1;
                    System.out.println(e.getMessage());
                }
            } while (state != 0);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}