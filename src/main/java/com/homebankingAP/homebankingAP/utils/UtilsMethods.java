package com.homebankingAP.homebankingAP.utils;


public final class UtilsMethods {
    private UtilsMethods() {
    }

    public static int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }

}
