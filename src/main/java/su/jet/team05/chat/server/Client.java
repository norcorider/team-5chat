package su.jet.team05.chat.server;

import java.net.Socket;

public class Client {
    Socket socket;
    String username;
    String room = "common";
    private static int counterId =0;

    public Client(Socket socket, String username) {
        this.socket = socket;
        this.username = username;
    }

    public Client(Socket socket) {
        this.socket = socket;
        username ="default"+ String.valueOf(counterId);
        counterId++;
    }

    public Socket getSocket() {
        return socket;
    }

    public String getUsername() {
        return username;
    }

    public String getRoom() { return room; }

    public void setUsername(String username) {

        this.username = username;
    }

    public void setRoom(String room) {

        this.room = room;
    }
}
