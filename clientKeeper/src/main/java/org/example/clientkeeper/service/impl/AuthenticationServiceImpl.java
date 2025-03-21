package org.example.clientkeeper.service.impl;

import org.example.clientkeeper.dto.request.AuthenticationRequest;
import org.example.clientkeeper.dto.request.RegisterRequest;
import org.example.clientkeeper.dto.response.AuthenticationResponse;
import org.example.clientkeeper.exception.CustomValidationException;
import org.example.clientkeeper.model.Client;
import org.example.clientkeeper.model.Role;
import org.example.clientkeeper.repository.ClientRepository;
import org.example.clientkeeper.repository.UtilisateurRepository;
import org.example.clientkeeper.service.AuthenticationService;
import org.example.clientkeeper.service.HistoriqueConnexionService;
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
    private UtilisateurRepository userRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    HistoriqueConnexionService historiqueConnexionService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Override
    public AuthenticationResponse register(RegisterRequest request) {
        validateRequest(request);

        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new CustomValidationException("Un utilisateur avec cet email existe déjà.");
        }

        if (clientRepository.findByCin(request.getCin()).isPresent()) {
            throw new CustomValidationException("Un utilisateur avec cette CIN existe déjà.");
        }

        var client = Client.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.ROLE_CLIENT)
                .nom(request.getNom())
                .prenom(request.getPrenom())
                .cin(request.getCin())
                .dateNaissance(request.getDateNaissance())
                .adresse(request.getAdresse())
                .profession(request.getProfession())
                .sexe(request.getSexe())
                .phoneNumber(request.getPhoneNumber())
                .numeroCompte("F1")
                .securePin(request.getSecurePin())
                .status(1)
                .solde(0.0)
                .build();

        clientRepository.save(client);
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

        var user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("Utilisateur non trouvé"));

        var jwtToken = jwtUtil.generateToken(user.getEmail());

        int status = 1;
        if (user instanceof Client) {
            Client client = (Client) user;
            status = client.getStatus();

            if (client.getStatus() == 1) {
                throw new RuntimeException("Votre compte est en attente de validation.");
            }

            historiqueConnexionService.enregistrerConnexion(client);
        }

        return AuthenticationResponse.builder()
                .token(jwtToken)
                .email(user.getEmail())
                .role(user.getRole().name())
                .status(status)
                .build();
    }

    private void validateRequest(RegisterRequest request) {
        if (request.getEmail() == null || request.getEmail().isBlank()) {
            throw new CustomValidationException("L'email est obligatoire.");
        }
        if (request.getPassword() == null || request.getPassword().isBlank()) {
            throw new CustomValidationException("Le mot de passe est obligatoire.");
        }
        if (request.getNom() == null || request.getNom().isBlank()) {
            throw new CustomValidationException("Le nom est obligatoire.");
        }
        if (request.getPrenom() == null || request.getPrenom().isBlank()) {
            throw new CustomValidationException("Le prénom est obligatoire.");
        }
        if (request.getCin() == null || request.getCin().isBlank()) {
            throw new CustomValidationException("La CIN est obligatoire.");
        }
    }
}
