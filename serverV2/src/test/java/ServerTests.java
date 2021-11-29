import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;
import java.util.LinkedList;

import intarface.Message;
import message.MyMessage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import thread.ServerSomthing;

public class ServerTests {
    private int port = 666;
    private final String ip = "127.0.0.1";
    public static LinkedList<ServerSomthing> serverList = new LinkedList<>();

    @Test
    void checkSendMessageAllClients() throws IOException, InterruptedException {
        Message expectedMessage = new MyMessage("Првиет", "Student", new Date());
        Runnable runnable = () -> {
            try {
                Thread.sleep(2000);
                Socket clientSocket = new Socket(ip, port);
                //создаю исходящий поток
                ObjectOutputStream out = new ObjectOutputStream(clientSocket.getOutputStream());
                out.writeObject(expectedMessage);
                out.flush();
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        };

        //Создаем сервер сокет для ожидания подключения клиентом
        ServerSocket serverSocket = new ServerSocket(port);
        new Thread(runnable).start();

        //Ожидаем подключение клиентом
        Socket socket = serverSocket.accept();
        //Создаем поток
        ServerSomthing serverSomthingMock = Mockito.spy(new ServerSomthing(socket));
        serverSomthingMock.interrupt();
        serverSomthingMock.start();
        serverSomthingMock.join();

        var captor = ArgumentCaptor.forClass(Message.class);
        Mockito.verify(serverSomthingMock).sendMessageAllClients(captor.capture());
        var message = captor.getValue();
        Assertions.assertTrue(assertEqualsMessage(expectedMessage, message));


    }

    boolean assertEqualsMessage(Message expectedMessage, Message actualMessage) {
        if (expectedMessage.equals(actualMessage)) {
            return true;
        }
        if (!expectedMessage.getMessageText().equals(actualMessage.getMessageText())) {
            return false;
        }
        if (!expectedMessage.getLogin().equals(actualMessage.getLogin())) {
            return false;
        }
        return true;
    }
}
