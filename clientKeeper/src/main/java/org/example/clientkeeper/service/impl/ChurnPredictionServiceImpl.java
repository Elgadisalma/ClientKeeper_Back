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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        String flaskUrl = "http://localhost:5000/predict";

        Map<String, Object> requestBody = Map.of("users", clientData);

        List<Map<String, Object>> predictions = restTemplate.postForObject(flaskUrl, requestBody, List.class);

        return predictions;
    }
}
