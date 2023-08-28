package com.homebankingAP.homebankingAP.controllers;

import com.homebankingAP.homebankingAP.models.Account;
import com.homebankingAP.homebankingAP.models.Client;
import com.homebankingAP.homebankingAP.dtos.ClientDTO;
import com.homebankingAP.homebankingAP.repositories.AccountRepository;
import com.homebankingAP.homebankingAP.repositories.ClientRepository;
import org.springframework.security.core.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import static java.util.stream.Collectors.toList;


@RestController
@RequestMapping("/api")
public class ClientController {

    @Autowired
    private ClientRepository _clientRepository;
    @Autowired
    private AccountRepository _accountRepository;

    @RequestMapping("/clients")
    public List<ClientDTO> getClients(){
        return _clientRepository.findAll().stream().map(ClientDTO::new).collect(toList());
    }

    @RequestMapping("/clients/{id}")
    public ClientDTO getClient(@PathVariable Long id){

        return new ClientDTO( _clientRepository.findById(id).orElse(null) );
    }


    //New client:
    @Autowired
    PasswordEncoder passwordEncoder;
    @RequestMapping(path = "/clients", method = RequestMethod.POST)
    public ResponseEntity<Object> register(
            @RequestParam String firstName,
            @RequestParam String lastName,
            @RequestParam String email,
            @RequestParam String password  ) {


        if (firstName.isBlank() || lastName.isBlank() || email.isBlank() || password.isBlank()) {
            return new ResponseEntity<>("Missing data", HttpStatus.FORBIDDEN);
        }

        if (_clientRepository.findByEmail(email) !=  null) {
            return new ResponseEntity<>("Name already in use", HttpStatus.FORBIDDEN);
        }

        Client client = new Client(firstName, lastName, email, passwordEncoder.encode(password));
        _clientRepository.save(client);

        LocalDate date = LocalDate.now();
        String numberAccount = Account.generateAccountNumber(_accountRepository);
        double balance = 0;

        Account account = new Account(numberAccount, date, balance);
        client.addAccount(account);
        _accountRepository.save(account);

        return new ResponseEntity<>(HttpStatus.CREATED);

    }

    @GetMapping("/clients/current")
    public ClientDTO getCurrentClient(Authentication authentication) {
        return new ClientDTO (_clientRepository.findByEmail(authentication.getName()));
    }

}
