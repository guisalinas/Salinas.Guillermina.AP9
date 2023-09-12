package com.homebankingAP.homebankingAP.utils;

import com.homebankingAP.homebankingAP.Services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;

public class UtilsMethods {

    public static int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }

}
