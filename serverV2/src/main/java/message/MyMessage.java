package message;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

import intarface.Message;

public class MyMessage extends Message {

    public MyMessage(String messageText, String login, Date date) {
        this.msg = messageText;
        this.login = login;
        this.date = date;
    }
}
