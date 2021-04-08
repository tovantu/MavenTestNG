package common;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

public class ReadProperties {

    public static Properties getInstanceFromResources(String fileName){
        String filePath = Utilities.getFilePathByOs(String.format("\\src\\main\\resources\\%s.properties", fileName));
        return getInstance(filePath);
    }

    public static Properties getInstanceDataTest(String fileName){
        String filePath = Utilities.getFilePathByOs(String.format("\\src\\main\\resources\\datatest\\%s.properties", fileName));
        return getInstance(filePath);
    }

    public static Properties getInstance(String filePath) {
        Properties properties = new Properties();
        try (InputStream inputStream = new FileInputStream(filePath)) {
            properties.load(inputStream);
            return properties;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
