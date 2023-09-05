package com.homebankingAP.homebankingAP.dtos;
import com.homebankingAP.homebankingAP.models.Loan;

import java.util.ArrayList;
import java.util.List;

public class LoanDTO {

    private Long id;
    private String name;
    private double maxAmount;
    private List<Integer> payments = new ArrayList<>();;

    public LoanDTO(Loan loan){
        this.id = loan.getId();
        this.name = loan.getName();
        this.maxAmount = loan.getMaxAmount();
        this.payments = loan.getPayments();
    }

    //getters

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getMaxAmount() {
        return maxAmount;
    }

    public List<Integer> getPayments() {
        return payments;
    }

    //setters

    public void setName(String name) {
        this.name = name;
    }

    public void setMaxAmount(double maxAmount) {
        this.maxAmount = maxAmount;
    }

    public void setPayments(List<Integer> payments) {
        this.payments = payments;
    }
}
