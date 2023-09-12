package com.homebankingAP.homebankingAP.utils;

import com.homebankingAP.homebankingAP.Services.AccountService;
import com.homebankingAP.homebankingAP.repositories.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class UtilsAccount {

    public static String generateAccountNumber(AccountService _accountService){
        int number;
        String numberAccount;

      do{
            number = UtilsMethods.getRandomNumber(1, 99999999);
            numberAccount = String.format("VIN-%08d", number);
        } while(_accountService.existsByNumber(numberAccount));

        return numberAccount;
    }
}
