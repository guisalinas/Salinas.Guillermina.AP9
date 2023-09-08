package com.homebankingAP.homebankingAP.Services;

import com.homebankingAP.homebankingAP.models.Card;
import org.springframework.stereotype.Service;

@Service
public interface CardService {
    void saveCard(Card card);
    String generateCardNumber();
    String generateCvv();
}
