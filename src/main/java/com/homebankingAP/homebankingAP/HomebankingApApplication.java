package com.homebankingAP.homebankingAP;

import com.homebankingAP.homebankingAP.Models.*;
import com.homebankingAP.homebankingAP.Repositories.AccountRepository;
import com.homebankingAP.homebankingAP.Repositories.ClientRepository;
import com.homebankingAP.homebankingAP.Repositories.LoanRepository;
import com.homebankingAP.homebankingAP.Repositories.TransactionRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;
import java.util.List;

@SpringBootApplication
public class HomebankingApApplication {

	public static void main(String[] args) {

		SpringApplication.run(HomebankingApApplication.class, args);
	}

	@Bean
	public CommandLineRunner initData(ClientRepository _clientRepository, AccountRepository _accountRepository, TransactionRepository _transactionRepository, LoanRepository _loanRepository){
		return (args) ->{

			//Client 1: Melba Morel
			Client melbaClient = _clientRepository.save(new Client("Melba", "Morel", "melba@mindhub.com"));
			Account account1 = new Account("VIN001", LocalDate.now(), 5000);
			Account account2 = new Account("VIN002", LocalDate.now().plusDays(1), 7500);
			melbaClient.addAccount(account1);
			melbaClient.addAccount(account2);
			_accountRepository.save(account1);
			_accountRepository.save(account2);

			//Client 2: Homero J Simpson
			Client homeroClient = _clientRepository.save(new Client("Homero J", "Simpson", "HomeroJay@mindhub.com"));
			Account account3 = new Account("VIN003", LocalDate.now(), 3200);
			homeroClient.addAccount(account3);
			_accountRepository.save(account3);

			//Transactions

			//Melba Morel accounts transactions:

			//Account 1 : VIN001
			Transaction transaction1 = _transactionRepository.save(new Transaction(TransactionType.CREDIT, 10000, "Wire transfer by HomeroJSimpson"));
			Transaction transaction2 = _transactionRepository.save(new Transaction(TransactionType.DEBIT, -1500, "MyStreaming Service"));
			account1.addTransaction(transaction1);
			account1.addTransaction(transaction2);
			_transactionRepository.save(transaction1);
			_transactionRepository.save(transaction2);
			//I save the account so that the new balance is saved
			_accountRepository.save(account1);

			//Account 2 : VIN002
			Transaction transaction3 = _transactionRepository.save(new Transaction(TransactionType.CREDIT, 3500, "Wire transfer by ElenaAlegria"));
			Transaction transaction4 = _transactionRepository.save(new Transaction(TransactionType.CREDIT, 3500, "Wire transfer by NedFlanders"));
			Transaction transaction5 = _transactionRepository.save(new Transaction(TransactionType.DEBIT, -4000, "Krusty-Burger"));

			account2.addTransaction(transaction3);
			account2.addTransaction(transaction4);
			account2.addTransaction(transaction5);
			_transactionRepository.save(transaction3);
			_transactionRepository.save(transaction4);
			_transactionRepository.save(transaction5);

			_accountRepository.save(account2);

			//Loans
			Loan mortgageLoan = new Loan("Mortgage", 500000, List.of(12, 24, 36, 48, 60));
			Loan personalLoan = new Loan("Personal", 100000, List.of(6, 12, 24));
			Loan carLoan = new Loan("Car", 500000, List.of(6, 12, 24, 36));

			_loanRepository.save(mortgageLoan);
			_loanRepository.save(personalLoan);
			_loanRepository.save(carLoan);

			//Melba loans
			ClientLoan melba_clientLoan1 = new ClientLoan(400000, 60, melbaClient, mortgageLoan);
			ClientLoan melba_clientLoan2 = new ClientLoan(50000, 12, melbaClient, personalLoan);
			melbaClient.addClientLoan(melba_clientLoan1);
			melbaClient.addClientLoan(melba_clientLoan2);
			_clientRepository.save(melbaClient);

			//Homero loans
			ClientLoan homero_clientLoan3 = new ClientLoan(100000, 24, homeroClient, personalLoan);
			ClientLoan homero_clientLoan4 = new ClientLoan(200000, 36, homeroClient, carLoan);
			homeroClient.addClientLoan(homero_clientLoan3);
			homeroClient.addClientLoan(homero_clientLoan4);
			_clientRepository.save(homeroClient);
		};
	}

}

