package com.homebankingAP.homebankingAP.Controllers;

import com.homebankingAP.homebankingAP.dtos.ClientDTO;
import com.homebankingAP.homebankingAP.Repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


import static java.util.stream.Collectors.toList;


@RestController
@RequestMapping("/api")
public class ClientController {

    @Autowired
    private ClientRepository C_repository;

    @RequestMapping("/clients")
    public List<ClientDTO> getClients(){
        return C_repository.findAll().stream().map(ClientDTO::new).collect(toList());
    }

    @RequestMapping("/clients/{id}")
    public ClientDTO getClient(@PathVariable Long id){

        return new ClientDTO( C_repository.findById(id).orElse(null));
    }

}
