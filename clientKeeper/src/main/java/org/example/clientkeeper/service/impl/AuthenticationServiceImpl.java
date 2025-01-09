package org.example.clientkeeper.service.impl;

import org.example.clientkeeper.dto.request.AuthenticationRequest;
import org.example.clientkeeper.dto.request.RegisterRequest;
import org.example.clientkeeper.dto.response.AuthenticationResponse;
import org.example.clientkeeper.model.Client;
import org.example.clientkeeper.model.Role;
import org.example.clientkeeper.repository.UtilisateurRepository;
import org.example.clientkeeper.service.AuthenticationService;
import org.example.clientkeeper.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    @Autowired
    UtilisateurRepository repository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JwtUtil jwtUtil;

    @Autowired
    AuthenticationManager authenticationManager;

    @Override
    public AuthenticationResponse register(RegisterRequest request) {
        var client = Client.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.client)
                .nom(request.getNom())
                .prenom(request.getPrenom())
                .dateNaissance(request.getDateNaissance())
                .adresse(request.getAdresse())
                .profession(request.getProfession())
                .sexe(request.getSexe())
                .phoneNumber(request.getPhoneNumber())
                .numeroCompte("F1")
                .securePin(request.getSecurePin())
                .status(0)
                .solde(0.0)
                .build();

        repository.save(client);
        var jwtToken = jwtUtil.generateToken(client.getEmail());

        return AuthenticationResponse.builder()
                .token(jwtToken)
                .email(client.getEmail())
                .role(client.getRole().name())
                .status(client.getStatus())
                .build();
    }

    @Override
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        var user = repository.findByEmail(request.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("Utilisateur non trouvé"));

        var jwtToken = jwtUtil.generateToken(user.getEmail());

        int status = 1; // Par défaut pour admin
        if (user instanceof Client) {
            status = ((Client) user).getStatus();
        }

        return AuthenticationResponse.builder()
                .token(jwtToken)
                .email(user.getEmail())
                .role(user.getRole().name())
                .status(status)
                .build();
    }
}