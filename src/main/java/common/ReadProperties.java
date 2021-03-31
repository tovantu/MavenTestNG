package common;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

public class ReadProperties {

    public static Properties getInstance(String fileName) {
        Properties properties = new Properties();
        String filePath = Utilities.getFilePathByOs(String.format("\\src\\main\\resources\\%s.properties", fileName));
        try (InputStream inputStream = new FileInputStream(filePath)) {
            properties.load(inputStream);
            return properties;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
