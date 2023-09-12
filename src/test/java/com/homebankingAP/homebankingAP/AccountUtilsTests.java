package com.homebankingAP.homebankingAP;

import com.homebankingAP.homebankingAP.Services.AccountService;
import com.homebankingAP.homebankingAP.utils.UtilsAccount;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@SpringBootTest
public class AccountUtilsTests {

    @Autowired
    private AccountService _accountService;

    @Test
    public void accountNumberIsCreated(){
        String accountNumber = UtilsAccount.generateAccountNumber(_accountService);
        assertThat(accountNumber,is(not(emptyOrNullString())));
    }
}
