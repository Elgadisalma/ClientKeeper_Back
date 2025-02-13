package org.example.clientkeeper.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.time.LocalDate;

import lombok.NoArgsConstructor;
import org.example.clientkeeper.model.Sexe;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {
    private String email;
    private String password;
    private String nom;
    private String prenom;
    private String cin;
    private LocalDate dateNaissance;
    private String adresse;
    private String profession;
    private Sexe sexe;
    private String phoneNumber;
    private String securePin;
}