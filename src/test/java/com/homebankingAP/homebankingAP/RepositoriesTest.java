package com.homebankingAP.homebankingAP;

import com.homebankingAP.homebankingAP.models.Loan;
import com.homebankingAP.homebankingAP.repositories.LoanRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.NONE;

import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import java.util.List;


@DataJpaTest
@AutoConfigureTestDatabase(replace = NONE)
public class RepositoriesTest {
    @Autowired
    private LoanRepository _loanRepository;

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


}
