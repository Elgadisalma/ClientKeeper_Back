package org.example.clientkeeper.model;

import lombok.Data;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
public class HistoriqueConnexion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime dateConnexion;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;
}
