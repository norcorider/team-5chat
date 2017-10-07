package su.jet.team05.chat.client;

import su.jet.team05.chat.exception.messageException;

import java.io.PrintWriter;
import java.util.Date;

public class Message {
    public static int parseMessage(String message, PrintWriter out) throws messageException {
        if (!isCommand(message)) throw new messageException("Message not a command");
        switch (getCommand(message)) {
            case "/snd":
                if (message.length() > 155) {
                    throw new messageException("Message is too large");
                } else {
                    if (message.length() > 5) {
                        out.println((new Date()) + ": " + message.substring(5));
                    } else {
                        throw new messageException("Message is empty");
                    }
                }
                return 1;
            case "/exit":
                return 0;
            default:
                throw new messageException("Incorrect command");
        }
    }

    private static boolean isCommand(String message) {
        return ((message.length() != 0) && (message.charAt(0) == '/'));
    }

    private static String getCommand(String message) {
        int firstSpace = message.indexOf(' ');
        return message.substring(0, firstSpace > 0 ? firstSpace : message.length());
    }
}
