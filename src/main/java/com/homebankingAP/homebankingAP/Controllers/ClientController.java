package com.homebankingAP.homebankingAP.controllers;

import com.homebankingAP.homebankingAP.Services.AccountService;
import com.homebankingAP.homebankingAP.Services.ClientService;
import com.homebankingAP.homebankingAP.models.Account;
import com.homebankingAP.homebankingAP.models.Client;
import com.homebankingAP.homebankingAP.dtos.ClientDTO;
import com.homebankingAP.homebankingAP.utils.UtilsAccount;
import com.homebankingAP.homebankingAP.utils.UtilsMethods;
import org.springframework.security.core.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;


@RestController
@RequestMapping("/api")
public class ClientController {

    @Autowired
    private ClientService _clientService;
    @Autowired
    private AccountService _accountService;

    @RequestMapping("/clients")
    public List<ClientDTO> getClients(){
        return _clientService.getClientsDTO();
    }

    @RequestMapping("/clients/{id}")
    public ClientDTO getClient(@PathVariable Long id){
        return _clientService.getClientDTO(id);
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

        if (_clientService.findClientByEmail(email) !=  null) {
            return new ResponseEntity<>("Name already in use", HttpStatus.FORBIDDEN);
        }

        Client client = new Client(firstName, lastName, email, passwordEncoder.encode(password));
        _clientService.saveClient(client);

        LocalDate date = LocalDate.now();
        String numberAccount = UtilsAccount.generateAccountNumber(_accountService);
        double balance = 0;

        Account account = new Account(numberAccount, date, balance);
        client.addAccount(account);
        _accountService.saveAccount(account);

        return new ResponseEntity<>(HttpStatus.CREATED);

    }

    @GetMapping("/clients/current")
    public ClientDTO getCurrentClient(Authentication authentication) {
        return _clientService.findClientByEmailDTO(authentication.getName());
    }

}
