package intarface;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public abstract class Message implements Serializable {

    private static final int serialVersionUID = 10;

    protected Date date;
    protected String login;
    protected String msg;

    public Date getDate() {
        return date;
    }

    public String getLogin() {
        return login;
    }

    public String getMessageText() {
        return msg;
    }

    @Override
    public String toString() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy hh:mm:ss");
        String dateInStringFormat = simpleDateFormat.format(date);
        return String.format("Сообщение от: %s %s \n" +
                "%s", login, dateInStringFormat, msg);
    }
}
