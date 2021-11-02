package logging;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

import intarface.Logging;
import intarface.Message;
import message.MyMessage;

public class MyLogging extends Logging {
    File folder = new File("log");
    File logFile = new File(folder.getPath() + "/serverLog.log");

    @Override
    public void log(Object object) {
        Message message = (MyMessage) object;
        createDir(folder);
        createFile(logFile);

        try {
            Files.write(Path.of(logFile.getPath()), (message.toString() + "\n").getBytes(), StandardOpenOption.APPEND);
        } catch (IOException e) {
            System.out.println(e);
        }
    }

}
