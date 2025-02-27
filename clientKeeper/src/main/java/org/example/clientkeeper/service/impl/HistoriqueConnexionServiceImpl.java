package org.example.clientkeeper.service.impl;

import org.example.clientkeeper.model.Client;
import org.example.clientkeeper.model.HistoriqueConnexion;
import org.example.clientkeeper.repository.HistoriqueConnexionRepository;
import org.example.clientkeeper.service.HistoriqueConnexionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class HistoriqueConnexionServiceImpl implements HistoriqueConnexionService {

    @Autowired
    HistoriqueConnexionRepository historiqueConnexionRepository;

    @Override
    public void enregistrerConnexion(Client client) {
        if (client.getStatus() != 0) {
            System.out.println("Connexion ignoree");
            return;
        }

        HistoriqueConnexion historique = new HistoriqueConnexion();
        historique.setClient(client);
        historique.setDateConnexion(LocalDateTime.now());
        historiqueConnexionRepository.save(historique);
    }

}
