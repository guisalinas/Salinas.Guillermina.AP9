package com.homebankingAP.homebankingAP.Models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static java.util.stream.Collectors.toList;

@Entity
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;

    @OneToMany(mappedBy="client", fetch=FetchType.EAGER)
    private Set<Account> accounts = new HashSet<>();

    @OneToMany(mappedBy="client", fetch=FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<ClientLoan> loans = new HashSet<>();

    @OneToMany(mappedBy = "client", fetch= FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<Card> cards = new HashSet<>();

    public Client(){

    }

    public Client(String firstName, String lastName, String email, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }

    //Getters
    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public Set<Account> getAccounts() {
        return accounts;
    }
    public Set<ClientLoan> getClientLoans(){ return loans;}

    public Set<Card> getCards() {
        return cards;
    }

    public String getPassword() {
        return password;
    }

    //Setters
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setAccounts(Set<Account> accounts) {
        this.accounts = accounts;
    }

    public void setLoans(Set<ClientLoan> clientLoans) {
        this.loans = clientLoans;
    }

    public void setCards(Set<Card> cards) {
        this.cards = cards;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    //other methods

    public void addAccount(Account account) {
        account.setClient(this);
        accounts.add(account);
    }

    public void addClientLoan(ClientLoan clientLoan){
        clientLoan.setClient(this);
        loans.add(clientLoan);
    }

    public void addCard(Card card){
        card.setClient(this);
        cards.add(card);
    }
    public List<Loan> getLoans() {
        return loans.stream().map(_loan -> _loan.getLoan()).collect(toList());
    }

    @Override
    public String toString(){ return this.firstName + " " + this.lastName ;}

}
