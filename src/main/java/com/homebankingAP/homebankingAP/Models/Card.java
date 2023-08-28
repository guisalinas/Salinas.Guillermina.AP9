package com.homebankingAP.homebankingAP.models;

import com.homebankingAP.homebankingAP.repositories.CardRepository;
import com.homebankingAP.homebankingAP.utils.UtilsMethods;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
public class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name="native", strategy = "native")
    private Long id;
    private String cardHolder;
    private CardType type;
    private CardColor color;
    private String number;
    private String cvv;
    private LocalDate thruDate;
    private LocalDate fromDate;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="client_id")
    private Client client;

    public Card(){

    }

    public Card(String cardHolder, CardType type, CardColor color, String number, String cvv, LocalDate thruDate, LocalDate fromDate) {
        this.cardHolder = cardHolder;
        this.type = type;
        this.color = color;
        this.number = number;
        this.cvv = cvv;
        this.thruDate = thruDate;
        this.fromDate = fromDate;
    }

    //getters
    public Long getId() {
        return id;
    }

    public String getCardHolder() {
        return cardHolder;
    }

    public CardType getType() {
        return type;
    }

    public CardColor getColor() {
        return color;
    }

    public String getNumber() {
        return number;
    }

    public String getCvv() {
        return cvv;
    }

    public LocalDate getThruDate() {
        return thruDate;
    }

    public LocalDate getFromDate() {
        return fromDate;
    }

    public Client getClient() {
        return client;
    }

    //setters

    public void setCardHolder(String cardHolder) {
        this.cardHolder = cardHolder;
    }

    public void setType(CardType type) {
        this.type = type;
    }

    public void setColor(CardColor color) {
        this.color = color;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public void setCvv(String cvv) {
        this.cvv = cvv;
    }

    public void setThruDate(LocalDate thruDate) {
        this.thruDate = thruDate;
    }

    public void setFromDate(LocalDate fromDate) {
        this.fromDate = fromDate;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    //other methods

    public static String generateCardNumber(CardRepository _cardRepository){

        StringBuilder numberCard = new StringBuilder();
        do{

            for (int i = 0; i < 4; i++ ) {
                numberCard.append(String.format("%04d", UtilsMethods.getRandomNumber(1, 9999)));

                if (i < 3) {
                    numberCard.append("-");
                }
            }

        } while(_cardRepository.existsByNumber(numberCard.toString()));

        return numberCard.toString();
    }

    public static String generateCvv(CardRepository _cardRepository){
        int number;
        String cvv;
        do{
            number = UtilsMethods.getRandomNumber(1, 999);
            cvv = String.format("%03d", number);
        } while(_cardRepository.existsByCvv(cvv));

        return cvv;
    }

}
