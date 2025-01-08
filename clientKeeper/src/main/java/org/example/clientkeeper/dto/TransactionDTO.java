package org.example.clientkeeper.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class TransactionDTO {
    private LocalDateTime dateTransaction;
    private double montant;
    private String motif;
}
