package su.jet.team05.chat.server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.*;

public class Server {
    private static final int PORT = 60000;

    private static Set<Socket> clients = new HashSet<>();


    public static void main(String[] args) {
        try (ServerSocket server = new ServerSocket(PORT)) {
            while (true) {
                Socket client = server.accept();

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
        HashSet<Socket> clientsToDelete = new HashSet<>();
        for (Socket current: clients ) {
            if (!current.isClosed()) {
                PrintWriter pw = new PrintWriter(current.getOutputStream(), true);
                pw.println(currentMessage);
            } else {
                System.out.println("Message " + currentMessage + " wasn't sent to " + current + " . This client will be deleted ");
                clientsToDelete.add(current);
            }
        }
        for( Socket toDelete : clientsToDelete){
            if( clients.contains(toDelete)){
                clients.remove(toDelete);
            }
        }
    }
}