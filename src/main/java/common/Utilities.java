package common;

public class Utilities {

    private static String OS = null;

    public static String getOsName()
    {
        if(OS == null) { OS = System.getProperty("os.name").toLowerCase(); }
        return OS;
    }
    public static boolean isWindows() {
        return (OS.indexOf("win") >= 0);
    }

    public static boolean isMac() {
        return (OS.indexOf("mac") >= 0);
    }

    public static boolean isLinux() {
        return (OS.indexOf("nix") >= 0
                || OS.indexOf("nux") >= 0
                || OS.indexOf("aix") > 0);
    }

    public static String getFilePathByOs(String path){
        getOsName();
        String filePath = Common.getResourceDirectory() + path;
        if(isMac()||isLinux()){
            return filePath.replace("\\","/");
        }
        return filePath.replace("\\","/");
    }
}
