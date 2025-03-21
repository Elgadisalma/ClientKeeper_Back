package org.example.clientkeeper.service.impl;

import jakarta.transaction.Transactional;
import org.example.clientkeeper.dto.TransactionDTO;
import org.example.clientkeeper.dto.TransactionDetailsDTO;
import org.example.clientkeeper.exception.CustomValidationException;
import org.example.clientkeeper.mapper.TransactionMapper;
import org.example.clientkeeper.model.Client;
import org.example.clientkeeper.model.Transaction;
import org.example.clientkeeper.model.Utilisateur;
import org.example.clientkeeper.repository.ClientRepository;
import org.example.clientkeeper.repository.TransactionRepository;
import org.example.clientkeeper.repository.UtilisateurRepository;
import org.example.clientkeeper.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    ClientRepository clientRepository;

    @Autowired
    TransactionMapper transactionMapper;

    @Autowired
    UtilisateurRepository utilisateurRepository;


    @Transactional
    @Override
    public void transferMoney(String senderEmail, TransactionDTO transactionDTO) {
        Utilisateur utilisateur = utilisateurRepository.findByEmail(senderEmail)
                .orElseThrow(() -> new CustomValidationException("Utilisateur envoyeur introuvable."));

        Client sender = clientRepository.findById(utilisateur.getId())
                .orElseThrow(() -> new CustomValidationException("Client envoyeur introuvable."));

        if (!sender.getSecurePin().equals(transactionDTO.getSecurePin())) {
            throw new CustomValidationException("Le code PIN sécurisé est incorrect.");
        }

        Client receiver = clientRepository.findByNomAndPrenomAndNumeroCompte(
                        transactionDTO.getReceiverName(),
                        transactionDTO.getReceiverPrenom(),
                        transactionDTO.getReceiverAccountNumber())
                .orElseThrow(() -> new CustomValidationException("Client receveur introuvable."));

        if (transactionDTO.getMontant() < 10) {
            throw new CustomValidationException("Le montant minimum pour une transaction est de 10 dh.");
        }

        if (sender.getSolde() < transactionDTO.getMontant()) {
            throw new CustomValidationException("Solde insuffisant pour effectuer cette transaction.");
        }

        sender.setSolde(sender.getSolde() - transactionDTO.getMontant());
        receiver.setSolde(receiver.getSolde() + transactionDTO.getMontant());

        clientRepository.save(sender);
        clientRepository.save(receiver);

        Transaction transaction = transactionMapper.toEntity(transactionDTO);
        transaction.setSender(sender);
        transaction.setReceiver(receiver);
        transaction.setDateTransaction(LocalDateTime.now());

        transactionRepository.save(transaction);
    }

    @Override
    public Map<LocalDate, List<TransactionDetailsDTO>> getUserTransactionHistory(String userEmail) {
        Utilisateur utilisateur = utilisateurRepository.findByEmail(userEmail)
                .orElseThrow(() -> new CustomValidationException("Utilisateur non trouvé."));

        Client client = clientRepository.findById(utilisateur.getId())
                .orElseThrow(() -> new CustomValidationException("Client introuvable."));

        // Transactions émises
        List<TransactionDetailsDTO> sentTransactions = transactionRepository.findBySender(client).stream()
                .map(transaction -> {
                    TransactionDetailsDTO dto = transactionMapper.toDetailsDTO(transaction);
                    dto.setTypeTransaction("emise");
                    return dto;
                })
                .toList();

        // Transactions reçues
        List<TransactionDetailsDTO> receivedTransactions = transactionRepository.findByReceiver(client).stream()
                .map(transaction -> {
                    TransactionDetailsDTO dto = transactionMapper.toDetailsDTO(transaction);
                    dto.setTypeTransaction("recue");
                    return dto;
                })
                .toList();

        // Fusion des transactions
        List<TransactionDetailsDTO> allTransactions = new ArrayList<>();
        allTransactions.addAll(sentTransactions);
        allTransactions.addAll(receivedTransactions);

        // Regroupement par date
        return allTransactions.stream()
                .collect(Collectors.groupingBy(dto -> dto.getDateTransaction().toLocalDate()));
    }


    @Override
    public List<TransactionDetailsDTO> getAllTransactions() {
        List<Transaction> transactions = transactionRepository.findAll();

        List<TransactionDetailsDTO> transactionDetailsDTOs = transactions.stream()
                .map(transaction -> {
                    TransactionDetailsDTO dto = new TransactionDetailsDTO();

                    dto.setSenderAccountNumber(transaction.getSender().getNumeroCompte());
                    dto.setSenderName(transaction.getSender().getNom());
                    dto.setSenderPrenom(transaction.getSender().getPrenom());

                    dto.setReceiverAccountNumber(transaction.getReceiver().getNumeroCompte());
                    dto.setReceiverName(transaction.getReceiver().getNom());
                    dto.setReceiverPrenom(transaction.getReceiver().getPrenom());

                    dto.setMontant(transaction.getMontant());
                    dto.setDateTransaction(transaction.getDateTransaction());
                    dto.setMotif(transaction.getMotif());

                    return dto;
                })
                .collect(Collectors.toList());

        return transactionDetailsDTOs;
    }

}
