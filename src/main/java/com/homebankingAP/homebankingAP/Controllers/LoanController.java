package com.homebankingAP.homebankingAP.controllers;

import com.homebankingAP.homebankingAP.dtos.LoanApplicationDTO;
import com.homebankingAP.homebankingAP.dtos.LoanDTO;
import com.homebankingAP.homebankingAP.models.*;
import com.homebankingAP.homebankingAP.repositories.AccountRepository;
import com.homebankingAP.homebankingAP.repositories.ClientRepository;
import com.homebankingAP.homebankingAP.repositories.LoanRepository;
import com.homebankingAP.homebankingAP.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("/api")
public class LoanController {

    @Autowired
    private ClientRepository _clientRepository;
    @Autowired
    private LoanRepository _loanRepository;
    @Autowired
    private AccountRepository _accountRepository;
    @Autowired
    private TransactionRepository _transactionRepository;

    @GetMapping("/loans")
    public List<LoanDTO> getLoans(){

        return _loanRepository.findAll().stream().map(LoanDTO::new).collect(toList());
    }

    @Transactional
    @RequestMapping(value="/loans", method=RequestMethod.POST)
    public ResponseEntity<Object> applyLoan(@RequestBody LoanApplicationDTO loanApplicationDTO, Authentication authentication){

        if (authentication != null){

            Client client = _clientRepository.findByEmail(authentication.getName());

            if(loanApplicationDTO.getLoanId() == null || loanApplicationDTO.getToAccountNumber().isBlank() || loanApplicationDTO.getPayments() == 0 || loanApplicationDTO.getAmount() == 0){
                return new ResponseEntity<>("Missing data", HttpStatus.FORBIDDEN);
            }

            if(loanApplicationDTO.getAmount() <= 0){
                return new ResponseEntity<>("You cannot apply for a loan less than or equal to 0.", HttpStatus.FORBIDDEN);
            }

            Account destinyAccount = _accountRepository.findByNumber(loanApplicationDTO.getToAccountNumber());

            if (destinyAccount == null){
                return new ResponseEntity<>("The destination account does not exist", HttpStatus.FORBIDDEN);
            }

            if (!destinyAccount.getClient().equals(client)){
                return new ResponseEntity<>("You cannot apply for a loan for someone else's account.", HttpStatus.FORBIDDEN);
            }

            Loan loan = _loanRepository.findById(loanApplicationDTO.getLoanId()).orElse(null);

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

            //transaction
            String description = loan.getName() + " Loan approved.";
            Transaction transaction = new Transaction(TransactionType.CREDIT, loanApplicationDTO.getAmount(), description);
            destinyAccount.addTransaction(transaction);
            _transactionRepository.save(transaction);
            _accountRepository.save(destinyAccount);
            _clientRepository.save(client);

            return new ResponseEntity<>("A loan has been successfully requested" ,HttpStatus.CREATED);
        }

        return new ResponseEntity<>("You are not logged it", HttpStatus.FORBIDDEN);
    }
}
