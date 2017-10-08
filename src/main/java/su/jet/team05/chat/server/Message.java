package su.jet.team05.chat.server;


import java.text.SimpleDateFormat;
import java.util.Date;

public class Message{

    private SimpleDateFormat dateMessage;
    private String userName;
    private String message;
    private static int countAnon = 0;
    private Date now;
    public Message(String userName, String message){
        this.userName = userName;
        this.message = message;
        now = new Date();
        dateMessage = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }

    public Message(String message){
        countAnon++;
        this.userName = "anonymous" + countAnon;
        this.message = message;
        now = new Date();
        dateMessage = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }

    @Override
    public String toString() {
            String result;
        //    Date now = new Date();
            String strDate = dateMessage.format(now);
            result = strDate + " " + userName + ": " + message;
            return result;

    }
/*
   @Override
    public int compareTo(Message o) {
        int  b;
        if (this.dateMessage.get2DigitYearStart().getTime() > o.dateMessage.get2DigitYearStart().getTime())
            b = 1;
            if(this.dateMessage == o.dateMessage)
            b = -1;
        else b = 0;
        return b;

    }*/

}

