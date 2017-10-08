package su.jet.team05.chat.server;


import java.text.DateFormat;
import java.util.Date;

public class Message {

    private Date dateMessage;
    private String userName;
    private String message;
    public Message(String userName, String message){
        this.userName = userName;
        this.message = message;
        dateMessage = new Date();
    }



    public String getMessage() {
        String strDate = DateFormat.getDateInstance(DateFormat.SHORT).format(dateMessage);

        return "";
    }

}