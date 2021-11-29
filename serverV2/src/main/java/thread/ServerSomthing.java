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
    }

    @Override
    public void run() {
        try {
            while (true) {
                System.out.println("Ожидаем сообщения от клиента " + Thread.currentThread().getName());
                Object objectIn = in.readObject();
                System.out.println("Получили сообщение от клиента");
                Message message = (Message) objectIn;
                System.out.println(message.toString());
                logging.log(message);
                System.out.println("Начинаем отправку сообщения всем клиентам.");
                sendMessageAllClients(message);
                System.out.println("Отправка сообщений клиентам окончен");
                if (Thread.currentThread().isInterrupted()) {
                   break;
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Произошла ошибка в подключении с клиентом.");
            e.printStackTrace();
        }
    }

    public void sendMessageAllClients(Message message) {
        for (ServerSomthing vr : ServerMain.serverList) {
            if (vr.equals(this)) {
                continue;
            }
            vr.send(message);
        }
    }

    private void send(Message msg) {
        try {
            out.writeObject(msg);
            out.flush();
        } catch (IOException exception) {
            System.err.println("Произошла ошибка отправки сообщения клиенту.");
            exception.printStackTrace();
        }
    }
}
