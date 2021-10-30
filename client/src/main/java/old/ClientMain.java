package old;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

import thread.ReadMessage;
import thread.WriteMessage;


public class ClientMain {
    private static final String ip = "127.0.0.1";
    private static final int PORT = 666;
    private static Socket socket;
    private static ObjectInputStream in;
    private static ObjectOutputStream out;
    private static Scanner scanner = new Scanner(System.in);
    private static String login;


    public static void main(String[] args) throws IOException {
        login = pressNickname();
        socket = new Socket(ip, PORT);
        try {
            in = new ObjectInputStream(socket.getInputStream());
            out = new ObjectOutputStream(socket.getOutputStream());
            new ReadMessage(in).start();
            new WriteMessage(out, login).start();
        } catch (IOException e) {
            ClientMain.downService();
        }
    }

    public static void downService() {
        try {
            if (!socket.isClosed()) {
                socket.close();
                in.close();
                out.close();
            }
        } catch (IOException ignored) {
        }
    }

    private static String pressNickname() {
        System.out.print("Press your nick: ");
        return scanner.nextLine();
    }
}
