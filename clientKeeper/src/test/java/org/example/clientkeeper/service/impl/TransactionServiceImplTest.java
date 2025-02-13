package org.example.clientkeeper.service.impl;

import org.example.clientkeeper.dto.TransactionDTO;
import org.example.clientkeeper.exception.CustomValidationException;
import org.example.clientkeeper.mapper.TransactionMapper;
import org.example.clientkeeper.model.Client;
import org.example.clientkeeper.model.Transaction;
import org.example.clientkeeper.model.Utilisateur;
import org.example.clientkeeper.repository.ClientRepository;
import org.example.clientkeeper.repository.TransactionRepository;
import org.example.clientkeeper.repository.UtilisateurRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TransactionServiceImplTest {

    @InjectMocks
    private TransactionServiceImpl transactionService;

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private ClientRepository clientRepository;

    @Mock
    private UtilisateurRepository utilisateurRepository;

    @Mock
    private TransactionMapper transactionMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void transferMoney_Success() {
        Utilisateur mockUtilisateur = new Utilisateur();
        mockUtilisateur.setId(1L);
        mockUtilisateur.setEmail("sender@example.com");

        Client mockSender = new Client();
        mockSender.setId(1L);
        mockSender.setSecurePin("1234");
        mockSender.setSolde(500.0);

        Client mockReceiver = new Client();
        mockReceiver.setId(2L);
        mockReceiver.setSolde(100.0);

        TransactionDTO mockTransactionDTO = new TransactionDTO();
        mockTransactionDTO.setSecurePin("1234");
        mockTransactionDTO.setMontant(100.0);
        mockTransactionDTO.setReceiverName("Receiver");
        mockTransactionDTO.setReceiverPrenom("Test");
        mockTransactionDTO.setReceiverAccountNumber("123456");

        when(utilisateurRepository.findByEmail("sender@example.com")).thenReturn(Optional.of(mockUtilisateur));
        when(clientRepository.findById(1L)).thenReturn(Optional.of(mockSender));
        when(clientRepository.findByNomAndPrenomAndNumeroCompte("Receiver", "Test", "123456"))
                .thenReturn(Optional.of(mockReceiver));
        when(transactionMapper.toEntity(mockTransactionDTO)).thenReturn(new Transaction());

        transactionService.transferMoney("sender@example.com", mockTransactionDTO);

        assertEquals(400.0, mockSender.getSolde());
        assertEquals(200.0, mockReceiver.getSolde());
        verify(clientRepository, times(2)).save(any(Client.class));
        verify(transactionRepository, times(1)).save(any(Transaction.class));
    }

    @Test
    void transferMoney_InvalidSenderPin() {
        Utilisateur mockUtilisateur = new Utilisateur();
        mockUtilisateur.setId(1L);
        mockUtilisateur.setEmail("sender@example.com");

        Client mockSender = new Client();
        mockSender.setId(1L);
        mockSender.setSecurePin("1234");

        TransactionDTO mockTransactionDTO = new TransactionDTO();
        mockTransactionDTO.setSecurePin("5678");

        when(utilisateurRepository.findByEmail("sender@example.com")).thenReturn(Optional.of(mockUtilisateur));
        when(clientRepository.findById(1L)).thenReturn(Optional.of(mockSender));

        CustomValidationException exception = assertThrows(
                CustomValidationException.class,
                () -> transactionService.transferMoney("sender@example.com", mockTransactionDTO)
        );

        assertEquals("Le code PIN sécurisé est incorrect.", exception.getMessage());
    }

    @Test
    void transferMoney_InsufficientBalance() {
        Utilisateur mockUtilisateur = new Utilisateur();
        mockUtilisateur.setId(1L);
        mockUtilisateur.setEmail("sender@example.com");

        Client mockSender = new Client();
        mockSender.setId(1L);
        mockSender.setSecurePin("1234");
        mockSender.setSolde(50.0);

        TransactionDTO mockTransactionDTO = new TransactionDTO();
        mockTransactionDTO.setSecurePin("1234");
        mockTransactionDTO.setMontant(100.0);

        when(utilisateurRepository.findByEmail("sender@example.com")).thenReturn(Optional.of(mockUtilisateur));
        when(clientRepository.findById(1L)).thenReturn(Optional.of(mockSender));

        CustomValidationException exception = assertThrows(
                CustomValidationException.class,
                () -> transactionService.transferMoney("sender@example.com", mockTransactionDTO)
        );

        assertEquals("Client receveur introuvable.", exception.getMessage());
    }


    @Test
    void transferMoney_InvalidReceiver() {
        Utilisateur mockUtilisateur = new Utilisateur();
        mockUtilisateur.setId(1L);
        mockUtilisateur.setEmail("sender@example.com");

        Client mockSender = new Client();
        mockSender.setId(1L);
        mockSender.setSecurePin("1234");

        TransactionDTO mockTransactionDTO = new TransactionDTO();
        mockTransactionDTO.setSecurePin("1234");

        when(utilisateurRepository.findByEmail("sender@example.com")).thenReturn(Optional.of(mockUtilisateur));
        when(clientRepository.findById(1L)).thenReturn(Optional.of(mockSender));
        when(clientRepository.findByNomAndPrenomAndNumeroCompte(anyString(), anyString(), anyString()))
                .thenReturn(Optional.empty());

        CustomValidationException exception = assertThrows(
                CustomValidationException.class,
                () -> transactionService.transferMoney("sender@example.com", mockTransactionDTO)
        );

        assertEquals("Client receveur introuvable.", exception.getMessage());
    }


}
