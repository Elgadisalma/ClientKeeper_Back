package org.example.clientkeeper.dto;

import lombok.Data;
import java.time.LocalDate;

@Data
public class AiModelDTO {
    private String modelFilePath;
    private double churnScore;
    private LocalDate datePrediction;
}
