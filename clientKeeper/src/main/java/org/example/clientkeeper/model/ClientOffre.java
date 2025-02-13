package org.example.clientkeeper.model;

import lombok.Data;
import jakarta.persistence.*;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "client_offre")
public class ClientOffre {

    @EmbeddedId
    private ClientOffreId id = new ClientOffreId();

    @ManyToOne
    @MapsId("clientId")
    @JoinColumn(name = "client_id")
    private Client client;

    @ManyToOne
    @MapsId("offreId")
    @JoinColumn(name = "offre_id")
    private Offre offre;

    @Column(name = "date_affectation", nullable = false)
    private LocalDate dateAffectation;
}
