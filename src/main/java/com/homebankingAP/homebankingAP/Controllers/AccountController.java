package com.homebankingAP.homebankingAP.Controllers;

import com.homebankingAP.homebankingAP.DTOs.AccountDTO;
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
    private AccountRepository A_repository;

    @RequestMapping("/accounts")
    public List<AccountDTO> getAccounts(){
        return A_repository.findAll().stream().map(AccountDTO::new).collect(toList());
    }

    @RequestMapping("/accounts/{id}")
    public AccountDTO getAccount(@PathVariable Long id){
        return new AccountDTO(A_repository.findById(id).orElse(null));
    }


}
