package com.homebankingAP.homebankingAP;

import com.homebankingAP.homebankingAP.Models.Client;
import com.homebankingAP.homebankingAP.Repositories.ClientRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class HomebankingApApplication {

	public static void main(String[] args) {

		SpringApplication.run(HomebankingApApplication.class, args);
	}

	@Bean
	public CommandLineRunner initData(ClientRepository repository){
		return (args) ->{
			repository.save(new Client("Melba", "Morel", "melba@mindhub.com"));
			repository.save(new Client("Homero J", "Simpson", "HomeroJay@mindhub.com"));

		};
	}


}

