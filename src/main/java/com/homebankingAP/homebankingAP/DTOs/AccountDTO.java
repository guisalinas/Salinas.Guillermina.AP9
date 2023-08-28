package com.homebankingAP.homebankingAP.dtos;

import com.homebankingAP.homebankingAP.models.Account;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class AccountDTO {
    private Long id;
    private String number;
    private LocalDate date;
    private double balance;
    private Set<TransactionDTO> transactions = new HashSet<>();

    public AccountDTO(Account account){
        this.id = account.getId();
        this.number = account.getNumber();
        this.date = account.getCreationDate();
        this.balance = account.getBalance();
        this.transactions = account.getTransactions().stream().map(transaction -> new TransactionDTO(transaction)).collect(Collectors.toSet());
    }

    //getters
    public Long getId() {
        return id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public LocalDate getDate() {
        return date;
    }

    public double getBalance() {
        return balance;
    }
    public Set<TransactionDTO> getTransactions() {
        return transactions;
    }

    //setters
    public void setDate(LocalDate creationDate) {
        this.date = creationDate;
    }
    public void setBalance(double balance) {
        this.balance = balance;
    }
    public void setTransactions(Set<TransactionDTO> transactions) {
        this.transactions = transactions;
    }

}
