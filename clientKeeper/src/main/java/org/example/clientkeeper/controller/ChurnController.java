package org.example.clientkeeper.controller;

import org.example.clientkeeper.service.ChurnPredictionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/churn")
public class ChurnController {

    @Autowired
    private ChurnPredictionService churnPredictionService;

    @GetMapping("/predict")
    public List<Map<String, Object>> predictChurnAutomatically() {
        List<Map<String, Object>> clientData = churnPredictionService.generateClientData();
        System.out.println("Données client : " + clientData);

        return churnPredictionService.predictChurn(clientData);
    }
}
