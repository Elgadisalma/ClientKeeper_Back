package org.example.clientkeeper.service.impl;

import org.example.clientkeeper.dto.request.AuthenticationRequest;
import org.example.clientkeeper.dto.request.RegisterRequest;
import org.example.clientkeeper.dto.response.AuthenticationResponse;
import org.example.clientkeeper.exception.CustomValidationException;
import org.example.clientkeeper.model.Client;
import org.example.clientkeeper.model.Role;
import org.example.clientkeeper.repository.ClientRepository;
import org.example.clientkeeper.repository.UtilisateurRepository;
import org.example.clientkeeper.util.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AuthenticationServiceImplTest {

    @InjectMocks
    private AuthenticationServiceImpl authenticationService;

    @Mock
    private UtilisateurRepository userRepository;

    @Mock
    private ClientRepository clientRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtUtil jwtUtil;

    @Mock
    private AuthenticationManager authenticationManager;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRegister_Success() {
        RegisterRequest request = new RegisterRequest("test@example.com", "password123", "John", "Doe", "CIN123", null, null, null, null, null, null);

        when(userRepository.findByEmail(request.getEmail())).thenReturn(Optional.empty());
        when(clientRepository.findByCin(request.getCin())).thenReturn(Optional.empty());
        when(passwordEncoder.encode(request.getPassword())).thenReturn("encodedPassword");
        when(jwtUtil.generateToken(request.getEmail())).thenReturn("jwtToken");

        AuthenticationResponse response = authenticationService.register(request);

        assertNotNull(response);
        assertEquals("jwtToken", response.getToken());
        assertEquals("test@example.com", response.getEmail());
        verify(clientRepository, times(1)).save(any(Client.class));
    }

    @Test
    void testRegister_EmailAlreadyExists() {
        RegisterRequest request = new RegisterRequest("test@example.com", "password123", "John", "Doe", "CIN123", null, null, null, null, null, null);

        when(userRepository.findByEmail(request.getEmail())).thenReturn(Optional.of(new Client()));

        CustomValidationException exception = assertThrows(CustomValidationException.class, () ->
                authenticationService.register(request));

        assertEquals("Un utilisateur avec cet email existe déjà.", exception.getMessage());
    }

//    @Test
//    void testAuthenticate_Success() {
//        AuthenticationRequest request = new AuthenticationRequest("test@example.com", "password123");
//        var client = Client.builder()
//                .email(request.getEmail())
//                .password("password123")
//                .role(Role.ROLE_CLIENT)
//                .build();
//
//        when(userRepository.findByEmail(request.getEmail())).thenReturn(Optional.of(client));
//        when(jwtUtil.generateToken(client.getEmail())).thenReturn("jwtToken");
//
//        AuthenticationResponse response = authenticationService.authenticate(request);
//
//        assertNotNull(response);
//        assertEquals("jwtToken", response.getToken());
//        assertEquals("test@example.com", response.getEmail());
//    }

    @Test
    void testAuthenticate_InvalidCredentials() {
        AuthenticationRequest request = new AuthenticationRequest("invalid@example.com", "wrongPassword");

        when(userRepository.findByEmail(request.getEmail())).thenReturn(Optional.empty());

        UsernameNotFoundException exception = assertThrows(UsernameNotFoundException.class, () ->
                authenticationService.authenticate(request));

        assertEquals("Utilisateur non trouvé", exception.getMessage());
    }

}