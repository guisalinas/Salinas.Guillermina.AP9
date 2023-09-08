package com.homebankingAP.homebankingAP.Services.Implements;

import com.homebankingAP.homebankingAP.Services.AccountService;
import com.homebankingAP.homebankingAP.dtos.AccountDTO;
import com.homebankingAP.homebankingAP.models.Account;
import com.homebankingAP.homebankingAP.repositories.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static java.util.stream.Collectors.toList;

public class AccountServiceImplement implements AccountService {

    @Autowired
    private AccountRepository _accountRepository;

    @Override
    public List<AccountDTO> getAccountsDTO() {
        return  _accountRepository.findAll().stream().map(AccountDTO::new).collect(toList());
    }

    @Override
    public Account findAccountById(Long id) {
        return _accountRepository.findById(id).orElse(null);
    }

    @Override
    public Account findAccountByNumber(String number) {
        return _accountRepository.findByNumber(number);
    }

    @Override
    public AccountDTO findAccountByNumberDTO(String number) {
        return new AccountDTO(this.findAccountByNumber(number));
    }

    @Override
    public AccountDTO getAccountDTO(Long id) {
        return new AccountDTO(this.findAccountById(id));
    }

    @Override
    public void saveAccount(Account account) {
        _accountRepository.save(account);
    }

    @Override
    public String generateNumber() {
        return Account.generateAccountNumber(_accountRepository);
    }
}
