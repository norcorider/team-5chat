package su.jet.team05.chat.client;

import su.jet.team05.chat.exception.messageException;

import java.io.PrintWriter;
import java.util.Date;

public class Message {
    private static final int MAX_MESSAGE_LENGTH = 150;
    private static final int MAX_NICK_LENGTH = 150;
    private static final String SEND_COMMAND = "/snd";
    private static final String EXIT_COMMAND = "/exit";
    private static final String SET_NICK_COMMAND = "/chid";
    private static final String GET_HISTORY_COMMAND = "/hist";

    public static int parseMessage(String message, PrintWriter out) throws messageException {
        if (!isCommand(message)) throw new messageException("Неправильный ввод");
        switch (getCommand(message)) {
            case SEND_COMMAND:
                if (getParameter(message).length() > MAX_MESSAGE_LENGTH) {
                    throw new messageException("Длина сообщения превышена, максимум 150 символов");
                } else {
                    if (message.length() > SEND_COMMAND.length() + 1) {
                        out.println("0" + message.substring(SEND_COMMAND.length() + 1));
                    } else {
                        throw new messageException("Пустое сообщение");
                    }
                }
                return 1;
            case EXIT_COMMAND:
                out.println("1");
                return 0;
            case SET_NICK_COMMAND:
                if (getParameter(message).length() > MAX_NICK_LENGTH) {
                    throw new messageException("Длина имени пользователя превышена");
                } else {
                    if (message.length() > SET_NICK_COMMAND.length() + 1) {
                        out.println("2" + message.substring(SET_NICK_COMMAND.length() + 1));
                    } else {
                        throw new messageException("Имя пользователя не введено");
                    }
                }
                return 1;
            case GET_HISTORY_COMMAND:
                out.println("3");
                return 1;

            default:
                throw new messageException("Неправильная команда");
        }
    }

    private static String getParameter(String message) {
        return message.indexOf(' ') == -1 ? "" : message.substring(message.indexOf(' '));
    }

    private static boolean isCommand(String message) {
        return ((message.length() != 0) && (message.charAt(0) == '/'));
    }

    private static String getCommand(String message) {
        int firstSpace = message.indexOf(' ');
        return message.substring(0, firstSpace > 0 ? firstSpace : message.length());
    }
}
