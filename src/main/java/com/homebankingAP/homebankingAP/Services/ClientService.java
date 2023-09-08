package com.homebankingAP.homebankingAP.Services;

import com.homebankingAP.homebankingAP.dtos.ClientDTO;
import com.homebankingAP.homebankingAP.models.Client;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ClientService{

    List<ClientDTO> getClientsDTO();
    Client findById(Long id);
    ClientDTO getClientDTO(Long id);
    void saveClient(Client client);
    Client findClientByEmail(String email);
    ClientDTO findClientByEmailDTO(String email);
}
