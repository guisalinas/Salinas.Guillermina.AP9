package com.homebankingAP.homebankingAP.Services;

import com.homebankingAP.homebankingAP.models.Card;

public interface CardService {
    void saveCard(Card card);
    String generateCardNumber();
    String generateCvv();
}
