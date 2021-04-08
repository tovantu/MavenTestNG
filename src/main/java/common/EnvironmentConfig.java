package common;

public class EnvironmentConfig {

    public static String getEnvironment(){
        String environment = getBranch();
        String url = Constant.DEV_URL;
        if(environment.equalsIgnoreCase("staging")){
            url = Constant.STAGING_URL;
        }else if(environment.equalsIgnoreCase("release")){
            url = Constant.RELEASE_URL;
        }
        return url;
    }

    public static String getBranch(){
        if(System.getProperty("branch")!= null){
            return System.getProperty("branch").toLowerCase();
        }
        return "dev";
    }

    public static String getBrowser(){
        if(System.getProperty("browser")!= null){
            return System.getProperty("browser").toLowerCase();
        }
        return "Chrome";
    }
}
