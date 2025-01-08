package org.example.clientkeeper.model;

import lombok.Data;
import java.time.LocalDate;

@Data
public class AiModel {
    private String modelFilePath;
    private double churnScore;
    private LocalDate datePrediction;
}
