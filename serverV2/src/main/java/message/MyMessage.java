package message;

import java.util.Date;

import intarface.Message;

public class MyMessage extends Message {

    public MyMessage(String messageText, String login, Date date) {
        this.msg = messageText;
        this.login = login;
        this.date = date;
    }
}
