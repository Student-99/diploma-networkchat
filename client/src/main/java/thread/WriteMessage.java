package thread;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Date;
import java.util.Scanner;

import message.MyMessage;
import old.ClientMain;

public class WriteMessage extends Thread {
    private ObjectOutputStream out;

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

                if (messageText.equals("stop")) {
                    ClientMain.downService();
                    break;
                } else {
                    out.writeObject(new MyMessage(new Date(), login, messageText));
                }
                out.flush();
            } catch (IOException e) {
                ClientMain.downService();
            }
        }
    }
}
