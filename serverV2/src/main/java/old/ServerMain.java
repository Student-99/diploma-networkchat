package old;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;
import java.util.Map;
import java.util.Properties;

import thread.ServerSomthing;
import workInFile.ReadFileProperties;

public class ServerMain {
    public static int port;
    public static LinkedList<ServerSomthing> serverList = new LinkedList<>();

    public static void main(String[] args) throws IOException {
        ReadFileProperties();
        ServerSocket serverSocket = new ServerSocket(port);
        try {
            while (true) {
                Socket socket = serverSocket.accept();
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
