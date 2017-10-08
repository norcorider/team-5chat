package su.jet.team05.chat.client;

import su.jet.team05.chat.exception.messageException;

import java.io.*;
import java.net.ConnectException;
import java.net.Socket;
import java.net.SocketException;
import java.util.Scanner;

import static java.lang.Thread.sleep;

public class Client {
    private static final String IP = "localhost";
    private static final int PORT = 60000;

    public static void main(String[] args) {
        try {
            ClientWork();
        } catch (ConnectException c) {
            System.out.println("Нет соединения с сервером");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void ClientWork() throws IOException {
        Socket client = null;
        while(client == null || !client.isConnected()) {
            try {
                client = new Socket(IP, PORT);
            }
            catch (SocketException s) {
                System.out.println("Нет соединения с сервером");
            }

            try {
                sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        PrintWriter out = new PrintWriter(client.getOutputStream(), true);
        BufferedReader reader = new BufferedReader(new InputStreamReader(client.getInputStream()));
        Scanner in = new Scanner(System.in);
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                int ReadState = 0;
                try {
                    while (ReadState == 0) {
                        System.out.println(reader.readLine());
                    }
                } catch (SocketException s) {
                    System.out.println("Нет соединения с сервером");
                    System.exit(-1);
                } catch (IOException e) {
                    e.printStackTrace();
                }
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
    }
}
