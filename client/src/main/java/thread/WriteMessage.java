package thread;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Date;
import java.util.Scanner;

import intarface.Logging;
import intarface.Message;
import logging.MyLogging;
import message.MyMessage;
import old.ClientMain;

public class WriteMessage extends Thread {

    private ObjectOutputStream out;
    private Logging logging = new MyLogging();

    private Scanner scanner = new Scanner(System.in);
    private String login;

    public WriteMessage(ObjectOutputStream out, String login) {
        this.login = login;
        this.out = out;
    }

    @Override
    public void run() {
        while (true) {
            try {
                String messageText = scanner.nextLine();
                if (messageText.equals("/exit")) {
                    ClientMain.downService();
                    break;
                }
                Message message = new MyMessage(messageText, login, new Date());
                logging.log(message);
                sendMessage(message);

            } catch (IOException e) {
                e.printStackTrace();
                ClientMain.downService();
            }
            if (Thread.currentThread().isInterrupted()){
                break;
            }
        }
    }

    public void sendMessage(Message msg) throws IOException {
        out.writeObject(msg);
        out.flush();
    }
}
