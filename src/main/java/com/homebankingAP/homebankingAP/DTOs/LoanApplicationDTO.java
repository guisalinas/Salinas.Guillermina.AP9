package com.homebankingAP.homebankingAP.dtos;

public class LoanApplicationDTO {
    private Long loanId;
    private double amount;
    private int payments;
    private String toAccountNumber;

    public LoanApplicationDTO(double amount, int payments, String toAccountNumber) {
        this.amount = amount;
        this.payments = payments;
        this.toAccountNumber = toAccountNumber;
    }

    //getters

    public Long getLoanId() {
        return loanId;
    }

    public double getAmount() {
        return amount;
    }

    public int getPayments() {
        return payments;
    }

    public String getToAccountNumber() {
        return toAccountNumber;
    }


    //setters


    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void setPayments(int payments) {
        this.payments = payments;
    }

    public void setToAccountNumber(String toAccountNumber) {
        this.toAccountNumber = toAccountNumber;
    }
}
