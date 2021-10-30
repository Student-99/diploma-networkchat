package message;

import java.util.Date;

import intarface.Message;

public class MyMessage extends Message {

    public MyMessage(Date date, String login, String messageText) {
        this.date = date;
        this.login = login;
        this.msg = messageText;
    }
}
