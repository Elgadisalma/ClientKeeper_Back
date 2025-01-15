package org.example.clientkeeper.service.impl;

import org.example.clientkeeper.exception.CustomValidationException;
import org.example.clientkeeper.model.Utilisateur;
import org.example.clientkeeper.repository.UtilisateurRepository;
import org.example.clientkeeper.service.EmailSenderService;
import org.example.clientkeeper.service.PasswordResetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class PasswordResetServiceImpl implements PasswordResetService {

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    @Autowired
    private EmailSenderService emailService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void sendPasswordResetLink(String email) {
        Utilisateur utilisateur = utilisateurRepository.findByEmail(email)
                .orElseThrow(() -> new CustomValidationException("Utilisateur introuvable avec cet email."));

        String token = UUID.randomUUID().toString();
        utilisateur.setPasswordResetToken(token);
        utilisateur.setTokenExpiryDate(LocalDateTime.now().plusHours(1));

        utilisateurRepository.save(utilisateur);

        String resetLink = "http://localhost:8080/api/auth/reset-password?token=" + token;
        emailService.sendVerificationEmail(email, "Réinitialisation de mot de passe", "Cliquez sur ce lien pour réinitialiser votre mot de passe : " + resetLink);
    }

    @Override
    public void resetPassword(String token, String newPassword) {
        Utilisateur utilisateur = utilisateurRepository.findByPasswordResetToken(token)
                .orElseThrow(() -> new CustomValidationException("Jeton invalide ou expiré."));

        if (utilisateur.getTokenExpiryDate().isBefore(LocalDateTime.now())) {
            throw new CustomValidationException("Jeton expiré.");
        }

        utilisateur.setPassword(passwordEncoder.encode(newPassword));
        utilisateur.setPasswordResetToken(null);
        utilisateur.setTokenExpiryDate(null);

        utilisateurRepository.save(utilisateur);
    }
}
