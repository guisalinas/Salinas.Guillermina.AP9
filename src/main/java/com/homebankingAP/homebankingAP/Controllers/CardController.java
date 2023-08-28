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

            Set<Card> cards = client.getCards();
            long creditCards = cards.stream().filter(card -> card.getType() == CardType.CREDIT).count();
            long debitCards = cards.stream().filter(card -> card.getType() == CardType.DEBIT).count();

            if (cardType == CardType.CREDIT && creditCards >= 3){
                return new ResponseEntity<>("You cannot have more than three cards for card type.", HttpStatus.FORBIDDEN);
            };

            if (cardType == CardType.DEBIT && debitCards >= 3){
                return new ResponseEntity<>("You cannot have more than three cards for card type.", HttpStatus.FORBIDDEN);
            };

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
