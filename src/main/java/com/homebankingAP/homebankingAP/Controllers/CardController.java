package com.homebankingAP.homebankingAP.Controllers;
import com.homebankingAP.homebankingAP.Services.CardService;
import com.homebankingAP.homebankingAP.Services.ClientService;
import com.homebankingAP.homebankingAP.dtos.CardDTO;
import com.homebankingAP.homebankingAP.models.*;
import com.homebankingAP.homebankingAP.utils.UtilsCard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("/api")
public class CardController {

    @Autowired
    private ClientService _clientService;
    @Autowired
    private CardService _cardService;

    @PostMapping("/clients/current/cards")
    public ResponseEntity<Object> createCard(Authentication authentication,
                                             @RequestParam CardColor cardColor,
                                             @RequestParam CardType cardType){

        if (authentication != null){

            Client client = _clientService.findClientByEmail(authentication.getName());

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
            }

            if (cardType == CardType.DEBIT){
                if (debitCards.size() < 3){
                    if (debitCards.stream().anyMatch(card -> card.getColor().equals(cardColor))){
                        return new ResponseEntity<>("You already have such a card", HttpStatus.FORBIDDEN);
                    }
                } else{
                    return new ResponseEntity<>("You have reached the debit card limit", HttpStatus.FORBIDDEN);
                }
            }

            // create card
            String cardHolder = client.toString();
            String number = UtilsCard.generateCardNumber(_cardService);
            String  cvv = UtilsCard.generateCvv(_cardService);
            LocalDateTime thruDate = LocalDateTime.now().plusYears(5);
            LocalDateTime fromDate = LocalDateTime.now();

            Card card = new Card(cardHolder, cardType, cardColor, number, cvv, thruDate, fromDate);
            client.addCard(card);
            _cardService.saveCard(card);

            return new ResponseEntity<>(HttpStatus.CREATED);
        }

        return new ResponseEntity<>("You are not logged it", HttpStatus.FORBIDDEN);
    }

    @GetMapping("/clients/current/cards")
    public List<CardDTO> getCurrentAccounts(Authentication authentication){
        Client client = _clientService.findClientByEmail(authentication.getName());
        return client.getCards().stream().map(CardDTO::new).collect(toList());
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Object> deleteCard(@PathVariable("id") Long id, Authentication authentication){
        if (authentication != null){

            Client client = _clientService.findClientByEmail(authentication.getName());

            if (id == null){
                return new ResponseEntity<>("Please, indicate the ID card number", HttpStatus.FORBIDDEN);
            }

            Card cardToDelete = _cardService.getCardById(id);

            if (cardToDelete == null){
                return new ResponseEntity<>("There is no card with that ID", HttpStatus.FORBIDDEN);
            }

            _cardService.deleteCard(cardToDelete);
            return new ResponseEntity<>("The Card has been successfully removed" ,HttpStatus.CREATED);


        }
        return new ResponseEntity<>("You are not logged it", HttpStatus.FORBIDDEN);
    }
}
