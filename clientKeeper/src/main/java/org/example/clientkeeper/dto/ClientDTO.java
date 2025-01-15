package org.example.clientkeeper.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.clientkeeper.model.Sexe;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClientDTO {
    private String nom;
    private String prenom;
    private String cin;
    private LocalDate dateNaissance;
    private String adresse;
    private String profession;
    private Sexe sexe;
    private double solde;
    private String phoneNumber;
    private String email;
    private String numeroCompte;
}
