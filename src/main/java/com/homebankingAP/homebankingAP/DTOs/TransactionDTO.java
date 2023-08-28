package com.homebankingAP.homebankingAP.dtos;

import com.homebankingAP.homebankingAP.models.Transaction;
import com.homebankingAP.homebankingAP.models.TransactionType;

import java.time.LocalDate;

public class TransactionDTO {

    private Long id;
    private TransactionType type;
    private double amount;
    private String description;
    private LocalDate date = LocalDate.now();

    public TransactionDTO(Transaction transaction){
        this.id = transaction.getId();
        this.type = transaction.getType();
        this.amount = transaction.getAmount();
        this.description = transaction.getDescription();
    }

    //getters
    public Long getId() {
        return id;
    }

    public TransactionType getType() {
        return type;
    }

    public double getAmount() {
        return amount;
    }

    public String getDescription() {
        return description;
    }

    public LocalDate getDate() {
        return date;
    }


    // setters
    public void setType(TransactionType type) {
        this.type = type;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

}
