package com.homebankingAP.homebankingAP.controllers;

import com.homebankingAP.homebankingAP.dtos.AccountDTO;
import com.homebankingAP.homebankingAP.Repositories.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("/api")
public class AccountController {

    @Autowired
    private AccountRepository _accountRepository;

    @RequestMapping("/accounts")
    public List<AccountDTO> getAccounts(){
        return _accountRepository.findAll().stream().map(AccountDTO::new).collect(toList());
    }

    @RequestMapping("/accounts/{id}")
    public AccountDTO getAccount(@PathVariable Long id){
        return new AccountDTO(_accountRepository.findById(id).orElse(null));
    }


}
