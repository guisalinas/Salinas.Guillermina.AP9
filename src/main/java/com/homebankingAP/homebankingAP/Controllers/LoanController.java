package com.homebankingAP.homebankingAP.controllers;

import com.homebankingAP.homebankingAP.Services.*;
import com.homebankingAP.homebankingAP.dtos.LoanApplicationDTO;
import com.homebankingAP.homebankingAP.dtos.LoanDTO;
import com.homebankingAP.homebankingAP.models.*;
import com.homebankingAP.homebankingAP.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class LoanController {

    @Autowired
    private ClientService _clientService;
    @Autowired
    private LoanService _loanService;
    @Autowired
    private AccountService _accountService;
    @Autowired
    private TransactionService _transactionService;
    @Autowired
    private ClientLoanService _clientLoanService;

    @GetMapping("/loans")
    public List<LoanDTO> getLoans(){
        return _loanService.getLoansDTO();
    }

    @Transactional
    @PostMapping("/loans")
    public ResponseEntity<Object> applyLoan(@RequestBody LoanApplicationDTO loanApplicationDTO, Authentication authentication){

        if (authentication != null){

            Client client = _clientService.findClientByEmail(authentication.getName());

            if(loanApplicationDTO.getLoanId() == null || loanApplicationDTO.getToAccountNumber().isBlank() || loanApplicationDTO.getPayments() == 0 || loanApplicationDTO.getAmount() == 0){
                return new ResponseEntity<>("Missing data", HttpStatus.FORBIDDEN);
            }

            if(loanApplicationDTO.getAmount() <= 0){
                return new ResponseEntity<>("You cannot apply for a loan less than or equal to 0.", HttpStatus.FORBIDDEN);
            }

            Account destinyAccount = _accountService.findAccountByNumber(loanApplicationDTO.getToAccountNumber());

            if (destinyAccount == null){
                return new ResponseEntity<>("The destination account does not exist", HttpStatus.FORBIDDEN);
            }

            if (!destinyAccount.getClient().equals(client)){
                return new ResponseEntity<>("You cannot apply for a loan for someone else's account.", HttpStatus.FORBIDDEN);
            }

            Loan loan = _loanService.findLoanByID(loanApplicationDTO.getLoanId());

            if (loan == null){
                return new ResponseEntity<>("That requested loan does not exist", HttpStatus.FORBIDDEN);
            }

            if (loanApplicationDTO.getAmount() > loan.getMaxAmount()){
                return new ResponseEntity<>("The requested amount exceeds the maximum allowed", HttpStatus.FORBIDDEN);
            }

            if (!loan.getPayments().contains(loanApplicationDTO.getPayments())){
                return new ResponseEntity<>("The requested number of installments is not available.", HttpStatus.FORBIDDEN);
            }


            //requested loan
            double amountLoan = loanApplicationDTO.getAmount() + (loanApplicationDTO.getAmount() * 0.20);
            ClientLoan clientLoan = new ClientLoan(amountLoan, loanApplicationDTO.getPayments());
            client.addClientLoan(clientLoan);
            loan.addClientLoan(clientLoan);
            _clientLoanService.saveClientLoan(clientLoan);

            //transaction
            String description = loan.getName() + " Loan approved.";
            Transaction transaction = new Transaction(TransactionType.CREDIT, loanApplicationDTO.getAmount(), description);
            destinyAccount.addTransaction(transaction);
            _transactionService.saveTransaction(transaction);
            _accountService.saveAccount(destinyAccount);
            _clientService.saveClient(client);

            return new ResponseEntity<>("A loan has been successfully requested" ,HttpStatus.CREATED);
        }

        return new ResponseEntity<>("You are not logged it", HttpStatus.FORBIDDEN);
    }
}
