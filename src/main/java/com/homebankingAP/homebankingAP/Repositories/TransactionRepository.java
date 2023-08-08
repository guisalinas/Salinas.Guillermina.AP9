package com.homebankingAP.homebankingAP.Repositories;

import com.homebankingAP.homebankingAP.Models.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
}
