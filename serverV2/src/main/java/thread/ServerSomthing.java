package thread;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import intarface.Message;
import old.ServerMain;

public class ServerSomthing extends Thread {

    private ObjectInputStream in;
    private ObjectOutputStream out;

    public ServerSomthing(Socket socket) throws IOException {
        in = new ObjectInputStream(socket.getInputStream());
        out = new ObjectOutputStream(socket.getOutputStream());
        start();
    }

    @Override
    public void run() {
        Message message;
        try {
            while (true) {
                message = (Message) in.readObject();
                for (ServerSomthing vr : ServerMain.serverList) {
//                    if (vr.equals(this)) {
//                        continue;
//                    }
                    vr.send(message);
                }
            }
        } catch (IOException | ClassNotFoundException e) {
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
