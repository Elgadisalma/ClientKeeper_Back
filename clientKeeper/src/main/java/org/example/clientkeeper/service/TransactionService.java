package org.example.clientkeeper.service;

import jakarta.transaction.Transactional;
import org.example.clientkeeper.dto.TransactionDTO;
import org.springframework.beans.factory.annotation.Autowired;

public interface TransactionService {
    @Autowired


    @Transactional
    void transferMoney(String senderEmail, TransactionDTO transactionDTO);
}
