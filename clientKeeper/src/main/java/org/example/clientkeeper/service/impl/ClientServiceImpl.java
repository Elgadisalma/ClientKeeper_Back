package org.example.clientkeeper.service.impl;

import org.example.clientkeeper.dto.ClientDTO;
import org.example.clientkeeper.exception.CustomValidationException;
import org.example.clientkeeper.mapper.ClientMapper;
import org.example.clientkeeper.model.Client;
import org.example.clientkeeper.repository.ClientRepository;
import org.example.clientkeeper.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClientServiceImpl implements ClientService {

    @Autowired
    ClientMapper clientMapper;

    @Autowired
    ClientRepository clientRepository;

    @Override
    public ClientDTO getClientById(Long id) {
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new CustomValidationException("Client introuvable avec l'ID : " + id));
        return clientMapper.toDTO(client);
    }

    @Override
    public void approveClient(Long clientId) {
        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new CustomValidationException("Client non trouvé"));

        if (client.getStatus() == 1) {
            throw new CustomValidationException("Client déjà approuvé");
        }

        client.setStatus(1);
        clientRepository.save(client);
    }
}
