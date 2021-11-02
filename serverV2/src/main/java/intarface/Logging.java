package intarface;

import java.io.File;
import java.io.IOException;

public abstract class Logging {

    public abstract void log(Object object);

    protected static void createFile(File file) {
        if (!file.exists()) {
            try {
                if (!file.createNewFile()) {
                    System.err.println(String.format("Не удалось создать файл %s по пути %s", file.getName(),
                            file.getPath()));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    protected static void createDir(File file) {
        if (!file.exists()) {
            if (!file.mkdirs()) {
                System.err.println(String.format("Не удалось создать папку %s по пути %s", file.getName(),
                        file.getPath()));
            }

        }
    }
}
