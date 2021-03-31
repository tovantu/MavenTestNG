package common;

public class EnvironmentConfig {

    public static String getEnvironment(){
        String environment = System.getProperty("branch");
        String url = "https://www.facebook.com/";
        if(environment.equalsIgnoreCase("staging")){
            url = "https://www.google.com/";
        }else if(environment.equalsIgnoreCase("release")){
            url = "https://www.youtube.com/";
        }
        return url;
    }

    public static String getBrowser(){
        return System.getProperty("browser");
    }
}
