package old;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;

import thread.ServerSomthing;

public class ServerMain {
    public static final int PORT = 666;
    public static LinkedList<ServerSomthing> serverList = new LinkedList<>();

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(PORT);

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
}
