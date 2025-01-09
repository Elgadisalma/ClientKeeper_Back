package org.example.clientkeeper.dto.request;

import lombok.Data;
import java.time.LocalDate;
import org.example.clientkeeper.model.Sexe;

@Data
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