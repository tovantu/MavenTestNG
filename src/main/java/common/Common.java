package common;

import java.util.Random;

public class Common {

    private Common(){

    }

    public static int ranDomNumber(int min, int max){
        Random rand = new Random();
        return (int)(Math.random() * (max - min + 1) + min);
    }

    public static String getResourceDirectory(){
        return System.getProperty("user.dir");
    }
}
