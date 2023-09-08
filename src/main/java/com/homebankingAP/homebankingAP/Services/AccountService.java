package com.homebankingAP.homebankingAP.Services;

import com.homebankingAP.homebankingAP.dtos.AccountDTO;
import com.homebankingAP.homebankingAP.models.Account;

import java.util.List;

public interface AccountService {

    List<AccountDTO> getAccountsDTO();
    Account findAccountById(Long id);
    Account findAccountByNumber(String number);
    AccountDTO findAccountByNumberDTO(String number);
    AccountDTO getAccountDTO(Long id);
    void saveAccount(Account account);
    String generateNumber();
}
