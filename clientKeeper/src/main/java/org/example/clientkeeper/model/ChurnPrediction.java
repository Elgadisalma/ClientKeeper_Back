package org.example.clientkeeper.model;

import lombok.Data;
import java.time.LocalDate;

@Data
public class ChurnPrediction {
    private int churnScore;
    private LocalDate datePrediction;
}
