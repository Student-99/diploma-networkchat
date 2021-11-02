package thread;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import intarface.Logging;
import intarface.Message;
import logging.MyLogging;
import old.ServerMain;

public class ServerSomthing extends Thread {

    private ObjectInputStream in;
    private ObjectOutputStream out;
    private Logging logging = new MyLogging();

    public ServerSomthing(Socket socket) throws IOException {
        out = new ObjectOutputStream(socket.getOutputStream());
        out.flush();
        in = new ObjectInputStream(socket.getInputStream());
        start();
    }

    @Override
    public void run() {
        Message message;
        try {
            while (true) {
                Object objectIn = in.readObject();
                message = (Message) objectIn;
                logging.log(message);
                for (ServerSomthing vr : ServerMain.serverList) {
//                    if (vr.equals(this)) {
//                        continue;
//                    }
                    vr.send(message);
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void send(Message msg) {
        try {
            out.writeObject(msg);
            out.flush();
        } catch (IOException ignored) {
        }
    }
}
