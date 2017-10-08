package su.jet.team05.chat.server;

import java.io.*;
import java.util.LinkedList;
import java.util.List;

import static java.lang.System.exit;

public class Saver {
    private static final String HISTORY_PATH = "history.txt";
    private static List<Message> history = new LinkedList<>();
    private static ObjectOutputStream out;
    private static BufferedReader in;
    private static BufferedWriter out2;
    private static Message message;
    private static StringBuilder msg;


    static void saveMessage(Message message) throws IOException {
        //Сохраняем сообщения, сохраняет в файлик объект
        /**
         * Сохраняем сообщения в файле history.txt в виде объектов типа Message.
         */
        try {
            Saver.out2 = new BufferedWriter(new FileWriter(HISTORY_PATH, true));
        } catch (FileNotFoundException f) {
            System.out.println("Не удалось открыть файл для сохранения истории. История сохранена не будет.");
            f.printStackTrace();
            exit(-1);
        } catch (IOException e) {
            System.out.println("Не удалось открыть файл для сохранения истории. История сохранена не будет.");
            e.printStackTrace();
            exit(-2);
        }

        try {
//            out.writeObject(message);
//            out.close();
            out2.write(message.toString() + System.lineSeparator());
        } catch (IOException e) {
            System.out.println("Ошибка при записи истории в файл");
            e.printStackTrace();
            exit(-3);
        } finally {
            out2.close();
        }
        history.add(message);
    }

    static StringBuilder getHistory() throws IOException {
        String line;
        Saver.msg = new StringBuilder();
        try {
            Saver.in = new BufferedReader(new FileReader(HISTORY_PATH));
            while ((line = in.readLine()) != null) {
                msg.append(line);
                msg.append(System.lineSeparator());
            }
            return Saver.msg;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            Saver.in.close();
        }
//        try {
//            ObjectInputStream in = new ObjectInputStream(
//                    new BufferedInputStream(
//                            new FileInputStream(HISTORY_PATH)));
//            return (Message)in.readObject();
//        } catch (IOException e) {
//            System.out.println("Неудалось считать файл истории сообщений.");
//            return null;
//        } catch (ClassNotFoundException e) {
//            System.out.println("Неверный формат файла истории сообщений.");
//            e.printStackTrace();
//        }
        return null;
    }
}
