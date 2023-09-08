package com.homebankingAP.homebankingAP.Services.Implements;

import com.homebankingAP.homebankingAP.Services.TransactionService;
import com.homebankingAP.homebankingAP.models.Transaction;
import com.homebankingAP.homebankingAP.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransactionServiceImplement implements TransactionService {

    @Autowired
    private TransactionRepository _transactionRepository;
    @Override
    public void saveTransaction(Transaction transaction) {
        _transactionRepository.save(transaction);
    }
}
