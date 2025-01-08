package org.example.clientkeeper.dto;

import lombok.Data;
import java.time.LocalDate;

@Data
public class ChurnPredictionDTO {
    private int churnScore;
    private LocalDate datePrediction;
}
