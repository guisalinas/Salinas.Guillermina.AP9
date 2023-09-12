package com.homebankingAP.homebankingAP;

import com.homebankingAP.homebankingAP.models.*;
import com.homebankingAP.homebankingAP.repositories.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.NONE;

import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;


@DataJpaTest
@AutoConfigureTestDatabase(replace = NONE)
public class RepositoriesTest {
    @Autowired
    private LoanRepository _loanRepository;
    @Autowired
    private ClientRepository _clientRepository;
    @Autowired
    private AccountRepository _accountRepository;
    @Autowired
    private TransactionRepository _transactionRepository;
    @Autowired
    private CardRepository _cardRepository;

    @Test
    public void existloans(){
        List<Loan> loans = _loanRepository.findAll();
        assertThat(loans, is(not(empty())));
    }

    @Test

    public void existPersonalLoan(){
        List<Loan> loans = _loanRepository.findAll();
        assertThat(loans, hasItem(hasProperty("name", is("Personal"))));
    }

    @Test
    public void existClients(){
        List<Client> clients = _clientRepository.findAll();
        assertThat(clients, is(not(empty())));
    }

    @Test
    public void createClient(){
        Client client = new Client("John", "Doe", "John@Doe.com", "mypassword");
        _clientRepository.save(client);
        Client clientSaved =_clientRepository.findById(client.getId()).orElse(null);

        assertThat(clientSaved, equalTo(client));
    }


    @Test
    public void existAccount(){
        List<Account> accounts = _accountRepository.findAll();
        assertThat(accounts, is(not(empty())));
    }

    @Test
    public void createAccount(){
        Account account = new Account("vin-0099", LocalDate.now(), 0.0);
        _accountRepository.save(account);
        Account accountSaved = _accountRepository.findByNumber(account.getNumber());

        assertThat(accountSaved, equalTo(account));
    }

    @Test
    public void existTransaction(){
        List<Transaction> transactions = _transactionRepository.findAll();
        assertThat(transactions, is(not(empty())));
    }

    @Test
    public void createTransaction(){
        Transaction transaction = new Transaction(TransactionType.CREDIT, 10000, "Services pay");
        _transactionRepository.save(transaction);
        Transaction transactionSaved = _transactionRepository.findById(transaction.getId()).orElse(null);

        assertThat(transactionSaved, equalTo(transaction));
    }

    @Test
    public void existCard(){
        List<Card> cards = _cardRepository.findAll();
        assertThat(cards, is(not(empty())));
    }

    @Test
    public void createCard(){
        Client client = new Client("Ann", "Smith", "Ann@Smith.com","myFavoritePassword");
        _clientRepository.save(client);
        Card card =  new Card(client.toString(), CardType.DEBIT, CardColor.GOLD,"0934-9388-5874", "756", LocalDateTime.now().plusYears(5), LocalDateTime.now());
        _cardRepository.save(card);

        Card cardSaved = _cardRepository.findById(card.getId()).orElse(null);
        assertThat(cardSaved, equalTo(card));
    }

}
