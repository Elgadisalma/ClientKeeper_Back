package org.example.clientkeeper.service;

import org.example.clientkeeper.dto.ClientDTO;
import org.example.clientkeeper.dto.ClientOffreDTO;

import java.util.List;

public interface ClientService {
    ClientDTO getClientById(Long id);

    void approveClient(Long clientId);

    void associateClientWithOffre(ClientOffreDTO clientOffreDTO);

    List<ClientDTO> getNoAppClients();
}
