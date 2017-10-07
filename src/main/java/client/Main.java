package client;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try {
            Socket client = new Socket("localhost", 60000);
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
                out.println(in.nextLine());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
