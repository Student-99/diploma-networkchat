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


    public static void main(String[] arg) throws IOException {
        System.out.println("Начинаем чтение конфига.");
        Map<String, String> prop = ReadFileProperties();
        System.out.println("Прочитали конфиг.");

        ip = prop.get("ADDRESS");
        port = Integer.parseInt(prop.get("PORT"));
        System.out.println("Получили данные ip и порта подключения.");

        login = pressNickname();
        System.out.println("Установка соединения с сервером...");
        socket = new Socket(ip, port);
        System.out.println("Соединение с сервером установлено.");
        try {
            out = new ObjectOutputStream(socket.getOutputStream());
            out.flush();
            in = new ObjectInputStream(socket.getInputStream());
            System.out.println("Чат готов к работе.");
            new ReadMessage(in);
            new WriteMessage(out, login).start();
        } catch (IOException e) {
            System.err.println("Произошла ошибка при работе клиентской части.");
            downService();
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
            System.err.println("произошла ошибка при закрытии потоков подключений");
            ignored.printStackTrace();
        }
    }

    private static String pressNickname() {
        System.out.print("Press your nick: ");
        return scanner.nextLine();
    }

    public static Map<String, String> ReadFileProperties() {
        return ReadFileProperties.readResourcesFile("ClientProp.properties");
    }
}
