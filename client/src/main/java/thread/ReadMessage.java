package thread;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

import intarface.Message;
import old.ClientMain;

public class ReadMessage extends Thread {
    private ObjectInputStream in;

    public ReadMessage(ObjectInputStream in) {
        this.in = in;
    }

    @Override
    public void run() {
        Message message;
        try {
            while (true) {
                message = (Message) in.readObject();
                System.out.println(message);
            }
        } catch (IOException | ClassNotFoundException e) {
            ClientMain.downService();
        }
    }
}
