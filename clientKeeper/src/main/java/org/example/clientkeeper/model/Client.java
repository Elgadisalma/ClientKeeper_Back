package org.example.clientkeeper.model;

import lombok.Data;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.Set;

@Data
@Entity
public class Client extends Utilisateur{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
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

    @ManyToMany
    @JoinTable(
            name = "client_offre",
            joinColumns = @JoinColumn(name = "client_id"),
            inverseJoinColumns = @JoinColumn(name = "offre_id")
    )
    private Set<Offre> offres;
}
