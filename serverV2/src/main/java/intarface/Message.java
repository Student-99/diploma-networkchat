package intarface;

import java.text.SimpleDateFormat;
import java.util.Date;

public abstract class Message {
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
        return String.format("%s %s %s", login, dateInStringFormat, msg);
    }
}
