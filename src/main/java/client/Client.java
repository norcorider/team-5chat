package client;

import java.io.*;
import java.net.Socket;
import java.sql.Time;
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
            new Thread(() -> {
                try {
                    while (true) {
                        System.out.println(reader.readLine());
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();
            while (true) {
                String message = in.nextLine();
                if(message.length() > 155)
                {
                    System.out.println("Too large lenght");
                } else if((message.length() >= 5) && (message.substring(0,5).equals("/snd ")))
                    out.println((new Date()) + ": " + message.substring(5));
                else {
                    System.out.println("Incorrect command");
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
