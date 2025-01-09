package org.example.clientkeeper.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Utilisateur {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    private String password;
    @Enumerated(EnumType.STRING)
    private Role role;
}
