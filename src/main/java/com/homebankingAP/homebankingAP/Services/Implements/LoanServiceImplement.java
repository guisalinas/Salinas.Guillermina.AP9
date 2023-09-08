package com.homebankingAP.homebankingAP.Services.Implements;

import com.homebankingAP.homebankingAP.Services.LoanService;
import com.homebankingAP.homebankingAP.dtos.LoanDTO;
import com.homebankingAP.homebankingAP.models.Loan;
import com.homebankingAP.homebankingAP.repositories.LoanRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static java.util.stream.Collectors.toList;

public class LoanServiceImplement implements LoanService {

    @Autowired
    private LoanRepository _loanRepository;

    @Override
    public List<LoanDTO> getLoansDTO() {
        return _loanRepository.findAll().stream().map(LoanDTO::new).collect(toList());
    }

    @Override
    public Loan findLoanByID(Long id) {
        return _loanRepository.findById(id).orElse(null);
    }

    @Override
    public LoanDTO getLoanDTO(Long id) {
        return new LoanDTO(this.findLoanByID(id));
    }


}
