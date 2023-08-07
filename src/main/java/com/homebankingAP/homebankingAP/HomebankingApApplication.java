package com.homebankingAP.homebankingAP;

import com.homebankingAP.homebankingAP.Models.Account;
import com.homebankingAP.homebankingAP.Models.Client;
import com.homebankingAP.homebankingAP.Repositories.AccountRepository;
import com.homebankingAP.homebankingAP.Repositories.ClientRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;
import java.util.Set;

@SpringBootApplication
public class HomebankingApApplication {

	public static void main(String[] args) {

		SpringApplication.run(HomebankingApApplication.class, args);
	}

	@Bean
	public CommandLineRunner initData(ClientRepository C_repository, AccountRepository A_repository){
		return (args) ->{
			Client client1 = C_repository.save(new Client("Melba", "Morel", "melba@mindhub.com"));
			Account account1 = new Account("VIN001", LocalDate.now(), 5000);
			Account account2 = new Account("VIN002", LocalDate.now().plusDays(1), 7500);
			client1.addAccount(account1);
			client1.addAccount(account2);
			A_repository.save(account1);
			A_repository.save(account2);

			Client client2 = C_repository.save(new Client("Homero J", "Simpson", "HomeroJay@mindhub.com"));
			Account account3 = new Account("VIN003", LocalDate.now(), 3200);
			client2.addAccount(account3);
			A_repository.save(account3);

		};
	}


}

