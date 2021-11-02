package workInFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import old.ServerMain;

public class ReadFileProperties {
    public static Map<String, String> readResourcesFile(String fileName) {
        HashMap<String, String> hashMap = new HashMap<>();
        Properties property = new Properties();
        InputStream inputStream = ServerMain.class.getClassLoader().getResourceAsStream(fileName);

        try {
            property.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }


        for (String key : property.stringPropertyNames()) {
            String value = property.getProperty(key);
            hashMap.put(key, value);
        }
        return hashMap;
    }
}
