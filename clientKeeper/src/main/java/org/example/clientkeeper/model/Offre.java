package org.example.clientkeeper.model;

import lombok.Data;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
public class Offre {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private double taux;
    private LocalDate dateCreation;
    private LocalDate dateExpiration;
    private String description;

    @OneToMany(mappedBy = "offre", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ClientOffre> clientOffres = new HashSet<>();

}
