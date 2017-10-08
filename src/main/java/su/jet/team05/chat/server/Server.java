package su.jet.team05.chat.server;


import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.*;

public class Server {
    private static final int PORT = 60000;


    private static Set<Client> clients = new HashSet<>();

    // key userName if it not Anonimus , value it's socket
    private static Set<String> usedUserNames = new HashSet<>();

    public static void main(String[] args) {
        try (ServerSocket server = new ServerSocket(PORT, 1000)) {
            while (true) {
                Socket client = server.accept();
                Client currentClient = new Client(client);
                clients.add(currentClient);
                usedUserNames.add(currentClient.getUsername());
                new Thread(() -> clientLoop(currentClient)).start();

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void clientLoop(Client client) {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(client.getSocket().getInputStream()))) {
            String inputStringMessage;
            do {
                inputStringMessage = in.readLine();
                parseAndSend(client, inputStringMessage);
            } while (inputStringMessage != null);


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void parseAndSend(Client client, String inputStringMessage) throws IOException {
        // if input is command to exit or show history
        if (inputStringMessage.length() < 2 && inputStringMessage.length() > 0) {
            // if client ask to exit
            if (inputStringMessage.charAt(0) == '1') {
                System.out.print("Клиент " + client.getSocket() + " будет удален");
                clients.remove(client);
                if (usedUserNames.contains(client.getUsername())) {
                    usedUserNames.remove(client.getUsername());
                }
            } else if (inputStringMessage.charAt(0) == '3') {
                // здесь будет выведена история
                Socket currentSoket = client.getSocket();
                PrintWriter pw2 = new PrintWriter(currentSoket.getOutputStream(), true);
                pw2.println(Saver.getHistory());
            }
        } else if (inputStringMessage.length() > 1) {
            char code = inputStringMessage.charAt(0);
            // если пришло обычное сообщение
            if (code == '0') {
                String messageToSend = inputStringMessage.substring(1);
                Message currentMessage = new Message(client.getUsername(), messageToSend);
                Saver.saveMessage(currentMessage);
                sendToAll(currentMessage.toString(), client.getRoom());
            } else if (code == '2') {//если настроить username
                String userNick = inputStringMessage.substring(1);
                changeUserName(client, userNick);
            } else if (code == '4') {// если пришел номре команты
                String nameRoom = inputStringMessage.substring(1);
                client.setRoom(nameRoom);
            } else {
                // невалидный код
            }
        }
    }

    private static void changeUserName(Client client, String userNick) throws IOException {
        // if this user name is already busy
        if (usedUserNames.contains(userNick)) {
            PrintWriter pw = new PrintWriter(client.getSocket().getOutputStream(), true);
            pw.println("Имя пользователя " + userNick + " занято.Введите новое");
        } else {
            sendToAll("Пользователь " + client.getUsername() + " переименован в " + userNick, client.getRoom());
            usedUserNames.remove(client.getUsername());
            client.setUsername(userNick);
            usedUserNames.add(userNick);

        }
    }


    private static void sendToAll(String currentMessage, String room) throws IOException {
        HashSet<Client> clientsToDelete = new HashSet<>();
        for (Client current : clients) {
            Socket currentSocket = current.getSocket();
            if (!currentSocket.isClosed()) {
                if (room.equals(current.getRoom())) {
                    PrintWriter pw = new PrintWriter(currentSocket.getOutputStream(), true);
                    pw.println(currentMessage);
                }
            } else {
                System.out.println("Сообщение " + currentMessage + " не было отправлено " + current.getUsername() + " . Этот клиент не в сетии ");
                clientsToDelete.add(current);
                usedUserNames.remove(current.getUsername());
            }
        }
        for (Client toDelete : clientsToDelete) {
            if (clients.contains(toDelete)) {
                clients.remove(toDelete);
                if (usedUserNames.contains(toDelete.getUsername())) {
                    usedUserNames.remove(toDelete.getUsername());
                }

            }
        }
    }
}