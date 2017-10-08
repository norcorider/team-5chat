package su.jet.team05.chat.server;

import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.List;

public class Saver {
    private static final String path = "";
    private static List<Message> history = new LinkedList<>();

    static void saveMessage(Message message) {
        //Сохраняем сообщения, сохраняет в файлик объект
        history.add(message);

    }
    static String getHistory(PrintWriter pw2) {
        //Читаем пообъектно.
        for (Message oldMessage : history) {
            pw2.println(oldMessage);
        }
        return "";
    }


}
