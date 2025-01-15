package org.example.clientkeeper.model;

import lombok.Data;
import lombok.experimental.SuperBuilder;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Data
@Entity
@SuperBuilder
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "dtype")
public class Utilisateur {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    private String password;
    @Enumerated(EnumType.STRING)
    private Role role;
    private String passwordResetToken;
    private LocalDateTime tokenExpiryDate;

    public Utilisateur() {

    }
}