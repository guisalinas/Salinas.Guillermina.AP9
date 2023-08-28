package com.homebankingAP.homebankingAP.controllers;

import com.homebankingAP.homebankingAP.models.*;
import com.homebankingAP.homebankingAP.repositories.CardRepository;
import com.homebankingAP.homebankingAP.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class CardController {

    @Autowired
    ClientRepository _clientRepository;
    @Autowired
    CardRepository _cardRepository;

    @RequestMapping(path = "/clients/current/cards", method = RequestMethod.POST)
    public ResponseEntity<Object> createCard(Authentication authentication, CardColor cardColor, CardType cardType){

        if (authentication != null){

            Client client = _clientRepository.findByEmail(authentication.getName());

            //Check number of cards and type:
            Set<Card> cards = client.getCards();
            Set<Card> creditCards = cards.stream().filter(card -> card.getType() == CardType.CREDIT).collect(Collectors.toSet());
            Set<Card> debitCards = cards.stream().filter(card -> card.getType() == CardType.DEBIT).collect(Collectors.toSet());

            if (cardType == CardType.CREDIT){
                if (creditCards.size() < 3){
                    if (creditCards.stream().anyMatch(card -> card.getColor().equals(cardColor))){
                        return new ResponseEntity<>("You already have such a card", HttpStatus.FORBIDDEN);
                    }
                } else{
                    return new ResponseEntity<>("You have reached the credit card limit", HttpStatus.FORBIDDEN);
                }
            };

            if (cardType == CardType.DEBIT){
                if (debitCards.size() < 3){
                    if (debitCards.stream().anyMatch(card -> card.getColor().equals(cardColor))){
                        return new ResponseEntity<>("You already have such a card", HttpStatus.FORBIDDEN);
                    }
                } else{
                    return new ResponseEntity<>("You have reached the debit card limit", HttpStatus.FORBIDDEN);
                }
            };

            // create card
            String cardHolder = client.toString();
            String number = Card.generateCardNumber(_cardRepository);
            String  cvv = Card.generateCvv(_cardRepository);
            LocalDate thruDate = LocalDate.now().plusYears(5);
            LocalDate fromDate = LocalDate.now();

            Card card = new Card(cardHolder, cardType, cardColor, number, cvv, thruDate, fromDate);
            client.addCard(card);
            _cardRepository.save(card);

            return new ResponseEntity<>(HttpStatus.CREATED);
        }

        return new ResponseEntity<>("You are not logged it", HttpStatus.FORBIDDEN);
    }
}
