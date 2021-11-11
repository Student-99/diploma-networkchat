package old;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;
import java.util.Map;

import thread.ServerSomthing;
import workInFile.ReadFileProperties;

public class ServerMain {
    public static int port;
    public static LinkedList<ServerSomthing> serverList = new LinkedList<>();

    public static void main(String[] args) throws IOException {
        System.out.println("Стартуем чтение конфига.");
        ReadFileProperties();
        System.out.println("Прочитали конфиг.");
        System.out.println("Создаем сервер сокет.");
        ServerSocket serverSocket = new ServerSocket(port);
        System.out.println("Сервер сокет удачно создан.");
        try {
            while (true) {
                System.out.println("Ожидаем подключения к серверу.");
                Socket socket = serverSocket.accept();
                System.out.println("Произошло подключение е серверу");
                try {
                    serverList.add(new ServerSomthing(socket));
                } catch (IOException e) {
                    socket.close();
                }
            }
        } finally {
            serverSocket.close();
        }
    }

    private static void ReadFileProperties() {
        Map<String, String> value = ReadFileProperties.readResourcesFile("ServerProp.properties");
        port = Integer.parseInt(value.get("PORT"));
    }
}
