package logging;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import intarface.Message;

public class MyLogging implements intarface.Logging {
    private final String logFilePath = "../resources/log.log";

    @Override
    public void log(Object object) {
        Message message = (Message) object;
        File file = new File(logFilePath);
        createFile(file);

        try (FileOutputStream fileOutputStream = new FileOutputStream(logFilePath, true);
             ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream)) {
            objectOutputStream.writeObject(message.toString());

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    static void createFile(File file) {
        if (!file.exists()){
            try {
                if (file.createNewFile()) {
                    String s = String.format("Создали файл - %s по пути - %s\n",file.getName(),file.getAbsolutePath());
                    System.out.println(s);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
