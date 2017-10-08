package su.jet.team05.chat.test;

import java.util.Scanner;

import static java.lang.Thread.sleep;

public class test {
    public static void main(String[] args) {
//        (new Thread(
//                new Runnable() {
//                    @Override
//                    public void run() {
//                        for (int i = 0; i < 1000; i++) {
//                            try {
//                                sleep(2);
//                            } catch (InterruptedException e) {
//                                e.printStackTrace();
//                            }
//                            System.out.println(i);
//                        }
//                    }
//                }
//        )).start();
        StringBuilder text;
        text = new StringBuilder();
        Scanner sc = new Scanner(System.in);
        byte temp;
        while (sc.hasNextByte()){
            temp = sc.nextByte();
            System.out.println("byte: " + temp);
            text.append(temp);
        }
        System.out.println("result string: " + text.toString());
    }
}
