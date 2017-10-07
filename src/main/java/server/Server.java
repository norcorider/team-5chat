package server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Server {
    private static final int PORT = 60000;

    private static List<Socket> clients = new LinkedList<>();
    private static Queue<String> messages = new LinkedList<String>();


    public static void main(String[] args) {
        try (ServerSocket server = new ServerSocket(PORT)) {
            while (true) {
                Socket client = server.accept();
                // save client to our client collection
                clients.add(client);
                new Thread(() -> clientLoop(client)).start();

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void clientLoop(Socket client) {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()))) {

            String currentMessage;
            do {
                currentMessage = in.readLine();
                sendToAll(currentMessage);
            } while (currentMessage != null);


        } catch (SocketException t) {

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void sendToAll(String currentMessage) throws IOException {
        try {
            for (Socket current : clients) {
                System.out.println(clients.toString());
                if (current.isConnected()) {
                    PrintWriter pw = new PrintWriter(current.getOutputStream(),true);
                    pw.println(currentMessage);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}