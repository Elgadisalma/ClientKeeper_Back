package org.example.clientkeeper.controller;

import lombok.RequiredArgsConstructor;
import org.example.clientkeeper.dto.TransactionDTO;
import org.example.clientkeeper.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/transactions")
@RequiredArgsConstructor
public class TransactionController {

    @Autowired
    TransactionService transactionService;

    @PostMapping("/transfer")
    public ResponseEntity<String> transferMoney(@RequestBody TransactionDTO transactionRequest) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        transactionService.transferMoney(email, transactionRequest);

        return ResponseEntity.ok("Transaction effectuée avec succès.");
    }
}
