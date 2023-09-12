package com.homebankingAP.homebankingAP.controllers;

import com.homebankingAP.homebankingAP.Services.AccountService;
import com.homebankingAP.homebankingAP.Services.ClientService;
import com.homebankingAP.homebankingAP.models.Account;
import com.homebankingAP.homebankingAP.models.Client;
import com.homebankingAP.homebankingAP.dtos.AccountDTO;
import com.homebankingAP.homebankingAP.utils.UtilsAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("/api")
public class AccountController {

    @Autowired
    private AccountService _accountService;
    @Autowired
    private ClientService _clientService;

    @GetMapping("/accounts")
    public List<AccountDTO> getAccounts(){
        return _accountService.getAccountsDTO();
    }

    @GetMapping("/accounts/{id}")
    public ResponseEntity<Object> getAccount (@PathVariable Long id, Authentication authentication){
        if(authentication != null){

            Client client = _clientService.findClientByEmail(authentication.getName());
            Account account = _accountService.findAccountById(id);

            if (account == null){
                return new ResponseEntity<>("This account does not exist", HttpStatus.NOT_FOUND);
            }

            if (account.getClient().equals(client)){

                AccountDTO accountDTO = new AccountDTO(account);
                return new ResponseEntity<>(accountDTO, HttpStatus.ACCEPTED);
            } else{
                return new ResponseEntity<>("Sorry, you don't have access to this account.", HttpStatus.FORBIDDEN);
            }
        }
        return new ResponseEntity<>("You are not logged it", HttpStatus.FORBIDDEN);

    }

    //new account

    @PostMapping("/clients/current/accounts")
    public ResponseEntity<Object> createAccount(Authentication authentication){

        if (authentication != null){

            Client client = _clientService.findClientByEmail(authentication.getName());

            Set<Account> accounts = client.getAccounts();
            if(accounts.size() > 3){
                return new ResponseEntity<>("You cannot have more than three accounts.", HttpStatus.CONFLICT);
            }

            LocalDate date = LocalDate.now();
            String numberAccount = UtilsAccount.generateAccountNumber(_accountService);
            double balance = 0;

            Account account = new Account(numberAccount, date, balance);
            client.addAccount(account);
            _accountService.saveAccount(account);

            return new ResponseEntity<>(HttpStatus.CREATED);
        }

        return new ResponseEntity<>("You are not logged it", HttpStatus.FORBIDDEN);
    }

    @GetMapping("/clients/current/accounts")
    public List<AccountDTO> getCurrentAccounts(Authentication authentication){
        Client client = _clientService.findClientByEmail(authentication.getName());
        return client.getAccounts().stream().map(AccountDTO::new).collect(toList());
    }

}