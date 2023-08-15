package com.homebankingAP.homebankingAP.DTOs;

import com.homebankingAP.homebankingAP.Models.ClientLoan;

public class ClientLoanDTO {
    private Long id;

    private Long loanId;
    private String name;
    private double amount;
    private int payments;


    public ClientLoanDTO(ClientLoan clientLoan) {
        this.name = clientLoan.getLoan().getName();
        this.id = clientLoan.getId();
        this.amount= clientLoan.getAmount();
        this.payments = clientLoan.getPayments();
        this.loanId = clientLoan.getLoan().getId();
    }

    //getters
    public Long getId() {
        return id;
    }

    public double getAmount() {
        return amount;
    }

    public int getPayments() {
        return payments;
    }

    public String getName() {
        return name;
    }

    public Long getLoanId() {
        return loanId;
    }

    //setters

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void setPayments(int payments) {
        this.payments = payments;
    }

    public void setName(String name) {
        this.name = name;
    }
}
