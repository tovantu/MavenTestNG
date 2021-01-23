package uihelper;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

public class ReadProperties {

//    private static ReadProperties instance = null;
//    private Properties properties = new Properties();
//
//
//    public static ReadProperties getInstance() {
//        if (instance == null) {
//            instance = new ReadProperties();
//            instance.readConfig();
//        }
//        return instance;
//    }
//    public String getProperty(String key) {
//        return properties.getProperty(key);
//    }

    public static Properties getInstance(String fileName) {
        Properties properties = new Properties();
        String file = System.getProperty("user.dir") + String.format("\\src\\main\\resources\\%s.properties", fileName);
        try (InputStream inputStream = new FileInputStream(file)) {
            properties.load(inputStream);
            return properties;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
