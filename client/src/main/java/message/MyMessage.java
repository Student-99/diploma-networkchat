package message;

import java.util.Date;

import intarface.Logging;
import intarface.Message;
import logging.MyLogging;

public class MyMessage extends Message {

    private Logging logging = new MyLogging();

    public MyMessage(Date date, String login, String messageText) {
        this.date = date;
        this.login = login;
        this.msg = messageText;
        logging.log(this.toString());
    }
}

