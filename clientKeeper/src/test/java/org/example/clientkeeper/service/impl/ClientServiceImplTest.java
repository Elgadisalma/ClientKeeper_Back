package org.example.clientkeeper.service.impl;

import org.example.clientkeeper.dto.ClientDTO;
import org.example.clientkeeper.dto.ClientOffreDTO;
import org.example.clientkeeper.exception.CustomValidationException;
import org.example.clientkeeper.mapper.ClientMapper;
import org.example.clientkeeper.model.*;
import org.example.clientkeeper.repository.*;
import org.example.clientkeeper.service.EmailSenderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.time.LocalDate;
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

    @Mock
    private UtilisateurRepository utilisateurRepository;

    @Mock
    private OffreRepository offreRepository;

    @Mock
    private ClientOffreRepository clientOffreRepository;

    @Mock
    private EmailSenderService emailService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetClientById_Success() {
        Client client = new Client();
        client.setId(1L);
        client.setNom("John Doe");
        when(clientRepository.findById(1L)).thenReturn(Optional.of(client));
        when(clientMapper.toDTO(client)).thenReturn(new ClientDTO());

        ClientDTO result = clientService.getClientById(1L);

        assertNotNull(result);
        verify(clientRepository, times(1)).findById(1L);
        verify(clientMapper, times(1)).toDTO(client);
    }

    @Test
    void testGetClientById_NotFound() {
        when(clientRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(CustomValidationException.class, () -> clientService.getClientById(1L));
        assertEquals("Client introuvable avec l'ID : 1", exception.getMessage());
    }

    @Test
    void testApproveClient_Success() {
        Client client = new Client();
        client.setId(1L);
        client.setStatus(1);
        client.setEmail("test@example.com");
        client.setNom("John");

        when(clientRepository.findById(1L)).thenReturn(Optional.of(client));

        clientService.approveClient(1L, "123456789");

        assertEquals(0, client.getStatus());
        assertEquals("123456789", client.getNumeroCompte());
        verify(clientRepository, times(1)).save(client);
        verify(emailService, times(1)).sendVerificationEmail(eq("test@example.com"), anyString(), anyString());
    }

    @Test
    void testApproveClient_AlreadyApproved() {
        Client client = new Client();
        client.setId(1L);
        client.setStatus(0);
        when(clientRepository.findById(1L)).thenReturn(Optional.of(client));

        Exception exception = assertThrows(CustomValidationException.class, () -> clientService.approveClient(1L, "123456789"));
        assertEquals("Client déjà approuvé", exception.getMessage());
    }

    @Test
    void testAssociateClientWithOffre_Success() {
        Client client = new Client();
        client.setId(1L);
        client.setEmail("test@example.com");
        client.setNom("John");

        Offre offre = new Offre();
        offre.setId(2L);
        offre.setTaux(5.0);
        offre.setDateExpiration(LocalDate.now().plusDays(30));

        ClientOffreDTO clientOffreDTO = new ClientOffreDTO(1L, 2L);

        when(clientRepository.findById(1L)).thenReturn(Optional.of(client));
        when(offreRepository.findById(2L)).thenReturn(Optional.of(offre));
        when(clientOffreRepository.findById(any())).thenReturn(Optional.empty());

        clientService.associateClientWithOffre(clientOffreDTO);

        verify(clientOffreRepository, times(1)).save(any(ClientOffre.class));
        verify(emailService, times(1)).sendVerificationEmail(eq("test@example.com"), anyString(), anyString());
    }

    @Test
    void testDeleteClient_Success() {
        Client client = new Client();
        client.setId(1L);
        client.setStatus(1);

        Utilisateur utilisateur = new Utilisateur();
        utilisateur.setId(1L);

        when(clientRepository.findById(1L)).thenReturn(Optional.of(client));
        when(utilisateurRepository.findById(1L)).thenReturn(Optional.of(utilisateur));

        clientService.deleteClient(1L);

        verify(clientRepository, times(1)).delete(client);
        verify(utilisateurRepository, times(1)).delete(utilisateur);
    }

    @Test
    void testDeleteClient_AlreadyApproved() {
        Client client = new Client();
        client.setId(1L);
        client.setStatus(0);
        when(clientRepository.findById(1L)).thenReturn(Optional.of(client));

        Exception exception = assertThrows(CustomValidationException.class, () -> clientService.deleteClient(1L));
        assertEquals("Impossible de supprimer un client déjà approuvé !", exception.getMessage());
    }

    @Test
    void testGetClientByEmail_Success() {
        Client client = new Client();
        client.setEmail("test@example.com");
        when(utilisateurRepository.findByEmail("test@example.com")).thenReturn(Optional.of(client));
        when(clientMapper.toDTO(client)).thenReturn(new ClientDTO());

        ClientDTO result = clientService.getClientByEmail("test@example.com");

        assertNotNull(result);
        verify(clientMapper, times(1)).toDTO(client);
    }

    @Test
    void testGetClientByEmail_NotFound() {
        when(utilisateurRepository.findByEmail("test@example.com")).thenReturn(Optional.empty());

        Exception exception = assertThrows(UsernameNotFoundException.class, () -> clientService.getClientByEmail("test@example.com"));
        assertEquals("Utilisateur non trouvé", exception.getMessage());
    }
}
