package org.example.clientkeeper.service;

import jakarta.transaction.Transactional;
import org.example.clientkeeper.dto.TransactionDTO;
import org.example.clientkeeper.dto.TransactionDetailsDTO;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface TransactionService {
    void transferMoney(String senderEmail, TransactionDTO transactionDTO);

    Map<LocalDate, List<TransactionDetailsDTO>> getUserTransactionHistory(String userEmail);

    List<TransactionDetailsDTO> getAllTransactions();
}
