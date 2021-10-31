package thread;

import java.io.IOException;
import java.io.ObjectInputStream;

import intarface.Logging;
import intarface.Message;
import logging.MyLogging;
import old.ClientMain;

public class ReadMessage extends Thread {
    private ObjectInputStream in;
    private Logging logging = new MyLogging();

    public ReadMessage(ObjectInputStream in) {
        this.in = in;
    }

    @Override
    public void run() {
        try {
            while (true) {
                Message message = (Message) in.readObject();
                logging.log(message.toString());
                System.out.println(message);
            }
        } catch (IOException | ClassNotFoundException e) {
            ClientMain.downService();
        }
    }
}
