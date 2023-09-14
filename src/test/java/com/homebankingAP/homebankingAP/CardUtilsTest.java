package com.homebankingAP.homebankingAP;

import com.homebankingAP.homebankingAP.Services.CardService;
import com.homebankingAP.homebankingAP.Services.ClientService;
import com.homebankingAP.homebankingAP.models.Card;
import com.homebankingAP.homebankingAP.models.CardColor;
import com.homebankingAP.homebankingAP.models.CardType;
import com.homebankingAP.homebankingAP.models.Client;
import com.homebankingAP.homebankingAP.utils.UtilsCard;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@SpringBootTest //for unit tests
public class CardUtilsTest {

    @Autowired
    private CardService _cardService;

    @Autowired
    private ClientService _clientService;

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

    @Test
    public void cardDeleted_Service(){
        Client client = new Client("Ann", "Smith", "Ann@Smith.com","myFavoritePassword");
        _clientService.saveClient(client);
        Card card =  new Card(client.toString(), CardType.DEBIT, CardColor.GOLD,"0934-9388-5874", "756", LocalDateTime.now().plusYears(5), LocalDateTime.now());

        _cardService.saveCard(card);
        assertThat(_cardService.getCardById(card.getId()), is(notNullValue()));

        _cardService.deleteCard(card);
        assertThat(_cardService.getCardById(card.getId()), is(nullValue()));
    }
}
