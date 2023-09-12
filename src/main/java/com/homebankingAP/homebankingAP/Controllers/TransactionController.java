package com.homebankingAP.homebankingAP.controllers;
import com.homebankingAP.homebankingAP.Services.AccountService;
import com.homebankingAP.homebankingAP.Services.ClientService;
import com.homebankingAP.homebankingAP.Services.TransactionService;
import com.homebankingAP.homebankingAP.models.Account;
import com.homebankingAP.homebankingAP.models.Client;
import com.homebankingAP.homebankingAP.models.Transaction;
import com.homebankingAP.homebankingAP.models.TransactionType;
import com.homebankingAP.homebankingAP.repositories.AccountRepository;
import com.homebankingAP.homebankingAP.repositories.ClientRepository;
import com.homebankingAP.homebankingAP.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class TransactionController {

    @Autowired
    private TransactionService _transactionService;
    @Autowired
    private ClientService _clientService;
    @Autowired
    private AccountService _accountService;

    @Transactional
    @PostMapping("/transactions")
    public ResponseEntity<Object> generateTransaction(@RequestParam(name = "fromAccountNumber")String originAccountNumber,
                                                      @RequestParam (name = "toAccountNumber")String destinyAccountNumber,
                                                      @RequestParam double amount, @RequestParam String description,
                                                      Authentication authentication){

        if(authentication != null){

            Client client = _clientService.findClientByEmail(authentication.getName());

            if(description.isBlank() || destinyAccountNumber.isBlank() || originAccountNumber.isBlank()){
                return new ResponseEntity<>("Missing data", HttpStatus.FORBIDDEN);
            }

            if(amount <= 0){
                return new ResponseEntity<>("You cannot transfer a balance less than or equal to 0", HttpStatus.FORBIDDEN);
            }

            Account destinyAccount = _accountService.findAccountByNumber(destinyAccountNumber);

            if (destinyAccount == null){
                return new ResponseEntity<>("The destination account does not exist", HttpStatus.FORBIDDEN);
            }

            Account originAccount = _accountService.findAccountByNumber(originAccountNumber);

            if(originAccount == null){
                return new ResponseEntity<>("The origin account does not exist.", HttpStatus.FORBIDDEN);
            }

            if (originAccount.equals(destinyAccount)){
                return new ResponseEntity<>("You cannot transfer to the same originating account", HttpStatus.FORBIDDEN);
            }

            if (!originAccount.getClient().equals(client)){
                return new ResponseEntity<>("Sorry, you don't have access to origin account.", HttpStatus.FORBIDDEN);
            }

            if (originAccount.getBalance() < amount){
                return new ResponseEntity<>("Insufficient funds",HttpStatus.FORBIDDEN);
            }

            /*--------------------------------------*/
            String descriptionOrigin = description + " " + destinyAccountNumber;
            String descriptionDestiny = description + " " + originAccountNumber;

            Transaction originAccountTransaction = new Transaction(TransactionType.DEBIT, -amount, descriptionOrigin);
            _transactionService.saveTransaction(originAccountTransaction);
            originAccount.addTransaction(originAccountTransaction);
            _transactionService.saveTransaction(originAccountTransaction);

            Transaction destinyAccountTransaction = new Transaction(TransactionType.CREDIT, amount, descriptionDestiny);
            _transactionService.saveTransaction(destinyAccountTransaction);
            destinyAccount.addTransaction(destinyAccountTransaction);
            _transactionService.saveTransaction(destinyAccountTransaction);

            _accountService.saveAccount(originAccount);
            _accountService.saveAccount(destinyAccount);

            return new ResponseEntity<>("Successful transfer", HttpStatus.CREATED);

        }

        return new ResponseEntity<>("You are not logged it", HttpStatus.FORBIDDEN);
    }
}
