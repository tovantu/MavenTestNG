package common;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

public class ReadPropertiesSingleTon {
    private static ReadPropertiesSingleTon instance;
    private Properties properties = null;

    private ReadPropertiesSingleTon(){
        String filePath = Utilities.getFilePathByOs(String.format("\\src\\main\\resources\\datatest\\account.properties"));
        try (InputStream inputStream = new FileInputStream(filePath)) {
            properties.load(inputStream);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Properties getProperties(){
        return properties;
    }

    public static Properties getPropertiesSingleTon(){
        if(instance != null) return instance.getProperties();
        instance = new ReadPropertiesSingleTon();
        return instance.getProperties();
    }

}
