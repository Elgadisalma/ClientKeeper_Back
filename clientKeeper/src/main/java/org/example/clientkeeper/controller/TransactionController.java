package org.example.clientkeeper.controller;

import lombok.RequiredArgsConstructor;
import org.example.clientkeeper.dto.TransactionDTO;
import org.example.clientkeeper.dto.TransactionDetailsDTO;
import org.example.clientkeeper.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/transactions")
@RequiredArgsConstructor
public class TransactionController {

    @Autowired
    TransactionService transactionService;

    @PreAuthorize("hasRole('CLIENT') and @clientServiceImpl.hasActiveStatus(authentication.name)")
    @PostMapping("/transfer")
    public ResponseEntity<String> transferMoney(@RequestBody TransactionDTO transactionRequest) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        transactionService.transferMoney(email, transactionRequest);

        return ResponseEntity.ok("Transaction effectuée avec succès.");
    }

    @GetMapping("/history")
    public ResponseEntity<Map<LocalDate, List<TransactionDTO>>> getTransactionHistoryGrouped(Authentication authentication) {
        String userEmail = authentication.getName();
        Map<LocalDate, List<TransactionDTO>> groupedHistory = transactionService.getUserTransactionHistory(userEmail);
        return ResponseEntity.ok(groupedHistory);
    }


}
