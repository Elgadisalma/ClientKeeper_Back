package org.example.clientkeeper.service.impl;

import org.example.clientkeeper.dto.ClientDTO;
import org.example.clientkeeper.dto.ClientOffreDTO;
import org.example.clientkeeper.exception.CustomValidationException;
import org.example.clientkeeper.mapper.ClientMapper;
import org.example.clientkeeper.mapper.ClientOffreMapper;
import org.example.clientkeeper.model.Client;
import org.example.clientkeeper.model.ClientOffre;
import org.example.clientkeeper.model.ClientOffreId;
import org.example.clientkeeper.model.Offre;
import org.example.clientkeeper.repository.ClientOffreRepository;
import org.example.clientkeeper.repository.ClientRepository;
import org.example.clientkeeper.repository.OffreRepository;
import org.example.clientkeeper.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ClientServiceImpl implements ClientService {

    @Autowired
    ClientMapper clientMapper;

    @Autowired
    ClientRepository clientRepository;

    @Autowired
    OffreRepository offreRepository;

    @Autowired
    ClientOffreMapper clientOffreMapper;

    @Autowired
    ClientOffreRepository clientOffreRepository;



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

    @Override
    public void associateClientWithOffre(ClientOffreDTO clientOffreDTO) {
        Client client = clientRepository.findById(clientOffreDTO.getClientId())
                .orElseThrow(() -> new IllegalArgumentException("Client not found"));

        Offre offre = offreRepository.findById(clientOffreDTO.getOffreId())
                .orElseThrow(() -> new IllegalArgumentException("Offre not found with ID"));

        ClientOffreId clientOffreId = new ClientOffreId();
        clientOffreId.setClientId(clientOffreDTO.getClientId());
        clientOffreId.setOffreId(clientOffreDTO.getOffreId());

        ClientOffre clientOffre = clientOffreRepository.findById(clientOffreId).orElse(null);

        if (clientOffre != null) {
            clientOffre.setDateAffectation(LocalDate.now());
        } else {
            clientOffre = new ClientOffre();
            clientOffre.setId(clientOffreId);
            clientOffre.setClient(client);
            clientOffre.setOffre(offre);
            clientOffre.setDateAffectation(LocalDate.now());
        }

        clientOffreRepository.save(clientOffre);
    }

    @Override
    public List<ClientDTO> getNoAppClients() { // Changer ClientDTO -> List<ClientDTO>
        List<Client> clients = clientRepository.findByStatus(1);
        return clientMapper.toDTO(clients);
    }

}
