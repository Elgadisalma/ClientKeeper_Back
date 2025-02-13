package org.example.clientkeeper.service;

import java.util.List;
import java.util.Map;

public interface ChurnPredictionService {
    List<Map<String, Object>> generateClientData();

    List<Map<String, Object>> predictChurn(List<Map<String, Object>> clientData);
}
