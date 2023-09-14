package com.homebankingAP.homebankingAP.Services.Implements;

import com.homebankingAP.homebankingAP.Services.CardService;
import com.homebankingAP.homebankingAP.models.Card;
import com.homebankingAP.homebankingAP.repositories.CardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CardServiceImplement implements CardService {

    @Autowired
    private CardRepository _cardRepository;
    @Override
    public void saveCard(Card card) {
        _cardRepository.save(card);
    }

    @Override
    public boolean existsByNumber(String cardNumber) {
        return _cardRepository.existsByNumber(cardNumber);
    }

    @Override
    public boolean existsByCvv(String cvv) {
        return _cardRepository.existsByCvv(cvv);
    }

    @Override
    public Card getCardById(Long id) {
        return _cardRepository.findById(id).orElse(null);
    }

    @Override
    public void deleteCard(Card card) {
      _cardRepository.delete(card);
    }


}
