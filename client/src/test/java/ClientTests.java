import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Date;
import java.util.Map;

import intarface.Message;
import message.MyMessage;
import old.ServerMain;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import thread.WriteMessage;

import static old.ClientMain.ReadFileProperties;

public class ClientTests {
    private static String ip;
    private static int port;
    private static ObjectOutputStream objectOutputStreamMock = Mockito.mock(ObjectOutputStream.class);
    private WriteMessage writeMessageMock = Mockito.mock(WriteMessage.class);

    @BeforeAll
    static void beforeAll() {
        Map<String, String> prop = ReadFileProperties();
        ip = prop.get("ADDRESS");
        port = Integer.parseInt(prop.get("PORT"));
    }

    @Test
    void CheckSentMessage() throws IOException, InterruptedException {
        //отправляемое сообщение
        String msg = "Hi. My Name is Denis";
        //Логин пользователя
        String login = "My login";

        Message expectedMessage = new MyMessage(msg,login,new Date());

        //эмулирование ввода данных с клавиатуры
        InputStream inputStream = new ByteArrayInputStream(msg.getBytes());
        System.setIn(inputStream);

        //Поднимаю сервер
        new Thread(() -> {
            try {
                ServerMain.main(new String[1]);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
        //даю время на поднятие
        Thread.sleep(3_000);

        //создаю соединение клиента с сервером
        Socket socket = new Socket(ip, port);
        //создаю исходящий поток
        ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
        out.flush();

        //Создаю поток для отправки сообщения на сервер
        Thread write = new WriteMessage(out, login);
        //останавливаю поток чтоб он отработал только одну итерацию
        write.interrupt();
        write.start();
        //ожидание на то чтоб поток с отправкой сообщения отработал
        Thread.sleep(2_000);

        //получаю данные которые были отправлены на сервер
        var captor = ArgumentCaptor.forClass(Message.class);
        Mockito.verify(writeMessageMock,Mockito.times(1)).sendMessage(Mockito.any());
        var message = captor.getValue();

        Assertions.assertTrue(assertEqualsMessage(expectedMessage,message));
    }

    boolean assertEqualsMessage(Message expectedMessage, Message actualMessage){
        if (!expectedMessage.getMessageText().equals(actualMessage.getMessageText())){
            return false;
        }
        if (!expectedMessage.getLogin().equals(actualMessage.getLogin())){
            return false;
        }
        return true;
    }


}
