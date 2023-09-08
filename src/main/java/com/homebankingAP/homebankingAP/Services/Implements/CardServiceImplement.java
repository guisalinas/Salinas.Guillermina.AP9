package com.homebankingAP.homebankingAP.Services.Implements;

import com.homebankingAP.homebankingAP.Services.CardService;
import com.homebankingAP.homebankingAP.models.Card;
import com.homebankingAP.homebankingAP.repositories.CardRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class CardServiceImplement implements CardService {

    @Autowired
    private CardRepository _cardRepository;
    @Override
    public void saveCard(Card card) {
        _cardRepository.save(card);
    }

    @Override
    public String generateCardNumber() {
        return Card.generateCardNumber(_cardRepository);
    }

    @Override
    public String generateCvv() {
        return Card.generateCvv(_cardRepository);
    }
}
