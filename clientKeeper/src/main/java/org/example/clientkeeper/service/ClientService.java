package org.example.clientkeeper.service;

import org.example.clientkeeper.dto.ClientDTO;

public interface ClientService {
    ClientDTO getClientById(Long id);

    void approveClient(Long clientId);
}
