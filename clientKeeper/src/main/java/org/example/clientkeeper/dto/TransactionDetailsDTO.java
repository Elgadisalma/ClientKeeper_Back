package org.example.clientkeeper.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TransactionDetailsDTO {
    private String senderAccountNumber;
    private String senderName;
    private String senderPrenom;
    private String receiverAccountNumber;
    private String receiverName;
    private String receiverPrenom;
    private double montant;
    private LocalDateTime dateTransaction;
    private String motif;
}