package org.example.clientkeeper.service.impl;

import jakarta.transaction.Transactional;
import org.example.clientkeeper.dto.TransactionDTO;
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

import java.time.LocalDateTime;

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
}
