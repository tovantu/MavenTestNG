package uihelper;

import java.util.Random;

public class RanDomNumber {
    public static int ranDomNumber(int min, int max){
        Random rand = new Random();
        return (int)(Math.random() * (max - min + 1) + min);
    }
}
