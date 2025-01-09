package org.example.clientkeeper.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.Set;

@Data
@Entity
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class Client extends Utilisateur {
    private String nom;
    private String prenom;
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

    @ManyToMany
    @JoinTable(
            name = "client_offre",
            joinColumns = @JoinColumn(name = "client_id"),
            inverseJoinColumns = @JoinColumn(name = "offre_id")
    )
    private Set<Offre> offres;

    public Client() {
    }
}