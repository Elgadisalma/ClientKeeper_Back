package org.example.clientkeeper.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class TransactionDetailsDTO {
    private String senderName;
    private String senderPrenom;
    private String senderAccountNumber;
    private String receiverName;
    private String receiverPrenom;
    private String receiverAccountNumber;
    private LocalDateTime dateTransaction;
    private double montant;
    private String motif;
    private String typeTransaction;
}
