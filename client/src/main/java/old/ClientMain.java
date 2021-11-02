package old;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Map;
import java.util.Scanner;

import thread.ReadMessage;
import thread.WriteMessage;
import workInFile.ReadFileProperties;


public class ClientMain {
    private static String ip;
    private static int port;
    private static Socket socket;
    private static ObjectInputStream in;
    private static ObjectOutputStream out;
    private static Scanner scanner = new Scanner(System.in);
    private static String login;


    public static void main(String[] args) throws IOException {
        ReadFileProperties();
        login = pressNickname();
        socket = new Socket(ip, port);
        try {
            out = new ObjectOutputStream(socket.getOutputStream());
            out.flush();
            in = new ObjectInputStream(socket.getInputStream());
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

    private static void ReadFileProperties() {
        Map<String, String> value = ReadFileProperties.readResourcesFile("ClientProp.properties");
        ip = value.get("ADDRESS");
        port = Integer.parseInt(value.get("PORT"));
    }
}
