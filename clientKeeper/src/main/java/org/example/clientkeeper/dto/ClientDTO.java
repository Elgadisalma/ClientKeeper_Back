package org.example.clientkeeper.dto;

import lombok.Data;
import org.example.clientkeeper.model.Sexe;

import java.time.LocalDate;

@Data
public class ClientDTO {
    private String nom;
    private String prenom;
    private LocalDate dateNaissance;
    private String adresse;
    private String profession;
    private Sexe sexe;
    private double solde;
    private String phoneNumber;
    private String email;
    private String numeroCompte;
}
