package org.example.clientkeeper.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class TransactionDTO {
//    private Long receiverId;
    private String receiverName;
    private String receiverPrenom;
    private String receiverAccountNumber;
    private LocalDateTime dateTransaction;
    private double montant;
    private String motif;
    private String securePin;
    private String typeTransaction;
}
