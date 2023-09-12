package com.homebankingAP.homebankingAP;

import com.homebankingAP.homebankingAP.Services.CardService;
import com.homebankingAP.homebankingAP.utils.UtilsCard;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@SpringBootTest //for unit tests
public class CardUtilsTest {

    @Autowired
    private CardService _cardService;

    @Test
    public void cardNumberIsCreated(){
        String cardNumber = UtilsCard.generateCardNumber(_cardService);
        assertThat(cardNumber,is(not(emptyOrNullString())));
    }

    @Test
    public void cardCVVIsCreated(){
        String cardCVV = UtilsCard.generateCvv(_cardService);
        assertThat(cardCVV, is(not(emptyOrNullString())));
    }


}
