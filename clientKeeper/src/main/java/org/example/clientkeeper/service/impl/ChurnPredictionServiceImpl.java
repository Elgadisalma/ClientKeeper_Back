package org.example.clientkeeper.service.impl;

import org.example.clientkeeper.model.Client;
import org.example.clientkeeper.repository.ClientRepository;
import org.example.clientkeeper.repository.HistoriqueConnexionRepository;
import org.example.clientkeeper.repository.TransactionRepository;
import org.example.clientkeeper.service.ChurnPredictionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class ChurnPredictionServiceImpl implements ChurnPredictionService {
    private final RestTemplate restTemplate;

    @Autowired
    ClientRepository clientRepository;

    @Autowired
    HistoriqueConnexionRepository historiqueConnexionRepository;

    @Autowired
    TransactionRepository transactionRepository;

    public ChurnPredictionServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public List<Map<String, Object>> generateClientData() {
        List<Map<String, Object>> clientData = new ArrayList<>();
        LocalDateTime oneWeekAgo = LocalDateTime.now().minusWeeks(1);

        List<Client> activeClients = clientRepository.findByStatus(0);

        for (Client client : activeClients) {
            Map<String, Object> clientInfo = new HashMap<>();
            clientInfo.put("id", client.getId());

            int connectionCount = historiqueConnexionRepository.countByClientAndDateConnexionAfter(client, oneWeekAgo);
            clientInfo.put("connections", connectionCount);

            int transactionCount = transactionRepository.countBySenderAndDateTransactionAfter(client, oneWeekAgo);
            clientInfo.put("transactions", transactionCount);

            clientInfo.put("balance", client.getSolde());

            clientData.add(clientInfo);
        }

        return clientData;
    }


    @Override
    public List<Map<String, Object>> predictChurn(List<Map<String, Object>> clientData) {
        String flaskUrl = "http://ai:5000/predict";

        // Création du corps de la requête pour Flask
        Map<String, Object> requestBody = Map.of("users", clientData);

        // Envoi de la requête et récupération des prédictions
        List<Map<String, Object>> predictions = restTemplate.postForObject(flaskUrl, requestBody, List.class);

        // Liste finale avec les informations complètes
        List<Map<String, Object>> enrichedPredictions = new ArrayList<>();

        for (Map<String, Object> prediction : predictions) {
            Number idNumber = (Number) prediction.get("id");
            Long clientId = idNumber.longValue();

            Optional<Client> clientOpt = clientRepository.findById(clientId);

            if (clientOpt.isPresent()) {
                Client client = clientOpt.get();

                Map<String, Object> enrichedData = new HashMap<>(prediction);
                enrichedData.put("nom", client.getNom());
                enrichedData.put("prenom", client.getPrenom());
                enrichedData.put("numeroCompte", client.getNumeroCompte());
                enrichedData.put("cin", client.getCin());

                enrichedPredictions.add(enrichedData);
            }
        }


        return enrichedPredictions;
    }

}
