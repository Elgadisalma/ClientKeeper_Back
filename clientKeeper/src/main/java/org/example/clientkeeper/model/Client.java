package org.example.clientkeeper.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class Client extends Utilisateur {
    private String nom;
    private String prenom;
    private String cin;
    private LocalDate dateNaissance;
    private String adresse;
    private String profession;
    @Enumerated(EnumType.STRING)
    private Sexe sexe;
    private double solde;
    private String phoneNumber;
    private String numeroCompte;
    private String securePin;
    private int status;

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ClientOffre> clientOffres = new HashSet<>();


    public Client() {
    }
}