package com.homebankingAP.homebankingAP.Services;

import com.homebankingAP.homebankingAP.models.Card;
import org.springframework.stereotype.Service;


public interface CardService {
    void saveCard(Card card);
    boolean existsByNumber(String cardNumber);
    boolean existsByCvv(String cvv);
    Card getCardById(Long id);
    void deleteCard(Card card);

}
