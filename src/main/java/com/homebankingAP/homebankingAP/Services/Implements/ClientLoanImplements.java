package com.homebankingAP.homebankingAP.Services.Implements;

import com.homebankingAP.homebankingAP.Services.ClientLoanService;
import com.homebankingAP.homebankingAP.models.ClientLoan;
import com.homebankingAP.homebankingAP.repositories.ClientLoanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClientLoanImplements implements ClientLoanService {
    @Autowired
    private ClientLoanRepository _clientLoanRepository;
    @Override
    public void saveClientLoan(ClientLoan clientLoan) {
        _clientLoanRepository.save(clientLoan);
    }
}