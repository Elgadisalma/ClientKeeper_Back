package org.example.clientkeeper.service.impl;

import org.example.clientkeeper.service.ChurnPredictionService;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ChurnPredictionServiceImpl implements ChurnPredictionService {
    private final RestTemplate restTemplate;

    public ChurnPredictionServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public List<Map<String, Object>> generateClientData() {
        List<Map<String, Object>> clientData = new ArrayList<>();

        Map<String, Object> client1 = new HashMap<>();
        client1.put("id", 1);
        client1.put("connections", 8);
        client1.put("transactions", 4);
        client1.put("balance", 1000.0);

        Map<String, Object> client2 = new HashMap<>();
        client2.put("id", 2);
        client2.put("connections", 5);
        client2.put("transactions", 2);
        client2.put("balance", 500.0);

        Map<String, Object> client3 = new HashMap<>();
        client3.put("id", 3);
        client3.put("connections", 1);
        client3.put("transactions", 0);
        client3.put("balance", 200.0);

        clientData.add(client1);
        clientData.add(client2);
        clientData.add(client3);

        return clientData;
    }

    @Override
    public List<Map<String, Object>> predictChurn(List<Map<String, Object>> clientData) {
        String flaskUrl = "http://localhost:5000/predict"; // URL de l'API Flask

        // Préparer la structure JSON à envoyer
        Map<String, Object> requestBody = Map.of("users", clientData);

        // Envoyer les données à Flask et récupérer la réponse
        List<Map<String, Object>> predictions = restTemplate.postForObject(flaskUrl, requestBody, List.class);

        return predictions;
    }
}
