package org.example.clientkeeper.service.impl;

import org.example.clientkeeper.model.Client;
import org.example.clientkeeper.model.HistoriqueConnexion;
import org.example.clientkeeper.repository.HistoriqueConnexionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class HistoriqueConnexionServiceImplTest {

    @InjectMocks
    private HistoriqueConnexionServiceImpl historiqueConnexionService;

    @Mock
    private HistoriqueConnexionRepository historiqueConnexionRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void enregistrerConnexion_Success() {
        Client mockClient = new Client();
        mockClient.setId(1L);
        mockClient.setNom("Test Client");

        when(historiqueConnexionRepository.save(any(HistoriqueConnexion.class))).thenAnswer(invocation -> {
            HistoriqueConnexion historique = invocation.getArgument(0);
            historique.setId(1L);
            return historique;
        });

        historiqueConnexionService.enregistrerConnexion(mockClient);

        verify(historiqueConnexionRepository, times(1)).save(any(HistoriqueConnexion.class));
    }


    @Test
    void enregistrerConnexion_RepositoryThrowsException() {
        Client mockClient = new Client();
        mockClient.setId(1L);
        mockClient.setNom("Test Client");

        when(historiqueConnexionRepository.save(any(HistoriqueConnexion.class)))
                .thenThrow(new RuntimeException("Database error"));

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> historiqueConnexionService.enregistrerConnexion(mockClient)
        );

        assertEquals("Database error", exception.getMessage());
        verify(historiqueConnexionRepository, times(1)).save(any(HistoriqueConnexion.class));
    }
}
