package org.example.clientkeeper.service;

import jakarta.transaction.Transactional;
import org.example.clientkeeper.dto.TransactionDTO;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface TransactionService {
//    @Transactional
    void transferMoney(String senderEmail, TransactionDTO transactionDTO);

    Map<LocalDate, List<TransactionDTO>> getUserTransactionHistory(String userEmail);
}
