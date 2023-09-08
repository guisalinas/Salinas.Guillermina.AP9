package com.homebankingAP.homebankingAP.Services.Implements;

import com.homebankingAP.homebankingAP.Services.ClientService;
import com.homebankingAP.homebankingAP.dtos.ClientDTO;
import com.homebankingAP.homebankingAP.models.Client;
import com.homebankingAP.homebankingAP.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
public class ClientServiceImplement implements ClientService {

    @Autowired
    private ClientRepository _clientRepository;

    @Override
    public List<ClientDTO> getClientsDTO() {
        return _clientRepository.findAll().stream().map(ClientDTO::new).collect(toList());
    }

    @Override
    public Client findById(Long id) {
        return _clientRepository.findById(id).orElse(null);
    }

    @Override
    public Client findClientByEmail(String email) {
        return _clientRepository.findByEmail(email);
    }

    @Override
    public ClientDTO getClientDTO(Long id) {
        return new ClientDTO(this.findById(id));
    }

    @Override
    public void saveClient(Client client) {
        _clientRepository.save(client);
    }

    @Override
    public ClientDTO findClientByEmailDTO(String email) {
        return new ClientDTO(this.findClientByEmail(email));
    }


}
