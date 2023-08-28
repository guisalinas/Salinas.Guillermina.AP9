package com.homebankingAP.homebankingAP.utils;

public class UtilsMethods {
    public static int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }

}
