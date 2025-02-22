package org.example.clientkeeper.service.impl;

import org.example.clientkeeper.dto.ClientDTO;
import org.example.clientkeeper.exception.CustomValidationException;
import org.example.clientkeeper.mapper.ClientMapper;
import org.example.clientkeeper.model.Client;
import org.example.clientkeeper.repository.ClientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ClientServiceImplTest {

    @InjectMocks
    private ClientServiceImpl clientService;

    @Mock
    private ClientMapper clientMapper;

    @Mock
    private ClientRepository clientRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getClientById_Success() {
        Long clientId = 1L;
        Client mockClient = new Client();
        mockClient.setId(clientId);
        mockClient.setNom("Test Client");

        ClientDTO mockClientDTO = new ClientDTO();
        mockClient.setId(clientId);
        mockClientDTO.setNom("Test Client");

        when(clientRepository.findById(clientId)).thenReturn(Optional.of(mockClient));
        when(clientMapper.toDTO(mockClient)).thenReturn(mockClientDTO);

        ClientDTO result = clientService.getClientById(clientId);

        assertNotNull(result);
        assertEquals("Test Client", result.getNom());
        verify(clientRepository, times(1)).findById(clientId);
        verify(clientMapper, times(1)).toDTO(mockClient);
    }

    @Test
    void getClientById_NotFound() {
        Long clientId = 1L;
        when(clientRepository.findById(clientId)).thenReturn(Optional.empty());

        CustomValidationException exception = assertThrows(
                CustomValidationException.class,
                () -> clientService.getClientById(clientId)
        );

        assertEquals("Client introuvable avec l'ID : " + clientId, exception.getMessage());
        verify(clientRepository, times(1)).findById(clientId);
        verifyNoInteractions(clientMapper);
    }

//    @Test
//    void approveClient_Success() {
//        Long clientId = 1L;
//        Client mockClient = new Client();
//        mockClient.setId(clientId);
//        mockClient.setStatus(0);
//
//        when(clientRepository.findById(clientId)).thenReturn(Optional.of(mockClient));
//
//        clientService.approveClient(clientId);
//
//        assertEquals(1, mockClient.getStatus());
//        verify(clientRepository, times(1)).findById(clientId);
//        verify(clientRepository, times(1)).save(mockClient);
//    }
//
//    @Test
//    void approveClient_AlreadyApproved() {
//        Long clientId = 1L;
//        Client mockClient = new Client();
//        mockClient.setId(clientId);
//        mockClient.setStatus(1);
//
//        when(clientRepository.findById(clientId)).thenReturn(Optional.of(mockClient));
//
//        CustomValidationException exception = assertThrows(
//                CustomValidationException.class,
//                () -> clientService.approveClient(clientId)
//        );
//
//        assertEquals("Client déjà approuvé", exception.getMessage());
//        verify(clientRepository, times(1)).findById(clientId);
//        verify(clientRepository, never()).save(any());
//    }
//
//    @Test
//    void approveClient_NotFound() {
//        Long clientId = 1L;
//        when(clientRepository.findById(clientId)).thenReturn(Optional.empty());
//
//        CustomValidationException exception = assertThrows(
//                CustomValidationException.class,
//                () -> clientService.approveClient(clientId)
//        );
//
//        assertEquals("Client non trouvé", exception.getMessage());
//        verify(clientRepository, times(1)).findById(clientId);
//        verify(clientRepository, never()).save(any());
//    }
}
