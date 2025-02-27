package org.example.clientkeeper.repository;

import org.example.clientkeeper.model.Client;
import org.example.clientkeeper.model.HistoriqueConnexion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface HistoriqueConnexionRepository extends JpaRepository<HistoriqueConnexion, Long> {
    int countByClientAndDateConnexionAfter(Client client, LocalDateTime oneWeekAgo);
}
