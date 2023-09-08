package com.homebankingAP.homebankingAP.Services;

import com.homebankingAP.homebankingAP.dtos.LoanDTO;
import com.homebankingAP.homebankingAP.models.Loan;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface LoanService {
    List<LoanDTO> getLoansDTO();
    Loan findLoanByID(Long id);
    LoanDTO getLoanDTO(Long id);


}
