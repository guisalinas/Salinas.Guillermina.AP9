package com.homebankingAP.homebankingAP.utils;

import com.homebankingAP.homebankingAP.Services.CardService;

public final class UtilsCard {

    private UtilsCard() {
    }

    public static String generateCardNumber(CardService _cardService){

        StringBuilder numberCard = new StringBuilder();
        do{
            for (int i = 0; i < 4; i++ ) {
                numberCard.append(String.format("%04d", UtilsMethods.getRandomNumber(1, 9999)));
                if (i < 3) {
                    numberCard.append("-");
                }
            }
        } while(_cardService.existsByNumber(numberCard.toString()));
        return numberCard.toString();
    }

    public static String generateCvv(CardService _cardService){
        int number;
        String cvv;
        do{
            number = UtilsMethods.getRandomNumber(1, 999);
            cvv = String.format("%03d", number);
        } while(_cardService.existsByCvv(cvv));

        return cvv;
    }
}
