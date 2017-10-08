package su.jet.team05.chat.server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.*;

public class Server {
    private static final int PORT = 60000;

    private static Set<Socket> clients = new HashSet<>();
    private static List<Message> history = new LinkedList<>();
    // key userName if it not Anonimus , value it's socket
    private static HashMap<String, Socket> userNames = new HashMap<>();

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

            String inputStringMessage;
            do {
                inputStringMessage = in.readLine();
                parseAndSend(client, inputStringMessage);
                //sendToAll(inputStringMessage);
            } while (inputStringMessage != null);


        } catch (SocketException t) {

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void parseAndSend(Socket client, String inputStringMessage) throws IOException {
        if (inputStringMessage.length() < 2 && inputStringMessage.length() > 0) {
            if (inputStringMessage.charAt(0) == '1') {
                System.out.print("Client " + client + " will be deleted");
                clients.remove(client);
                // нужно как-то осободить ник клиента
                //userNames.remove(client);
            }
        } else if (inputStringMessage.length() > 1) {
            char code = inputStringMessage.charAt(0);
            // если пришло обычное сообщение
            if (code == '0') {
                String messageToSend = inputStringMessage.substring(1);
                sendToAll(messageToSend);
            } else if (code == '2') {//если настроить username
                String userNick = inputStringMessage.substring(1);
                // if this user name is stored
                if (userNames.containsKey(userNick)) {
                    // if it's not the same socket, send to this client that username is invalid
                    if (!(userNames.get(userNick) == client)) {
                        PrintWriter pw = new PrintWriter(client.getOutputStream(), true);
                        pw.println("The user name " + userNick + " is busy. Enter other username");
                    }
                } else {
                    userNames.put(userNick, client);
                }
            } else if (code == '3') {
                // здесь будет выведена история
            } else {
                // невалидный код
            }
        }
    }

    private static void sendToAll(String currentMessage) throws IOException {
        HashSet<Socket> clientsToDelete = new HashSet<>();
        for (Socket current : clients) {
            if (!current.isClosed()) {
                PrintWriter pw = new PrintWriter(current.getOutputStream(), true);
                pw.println(currentMessage);
            } else {
                System.out.println("Message " + currentMessage + " wasn't sent to " + current + " . This client will be deleted ");
                clientsToDelete.add(current);
            }
        }
        for (Socket toDelete : clientsToDelete) {
            if (clients.contains(toDelete)) {
                clients.remove(toDelete);
            }
        }
    }
}