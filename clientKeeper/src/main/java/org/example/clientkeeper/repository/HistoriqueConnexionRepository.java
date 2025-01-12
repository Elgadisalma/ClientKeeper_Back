package org.example.clientkeeper.repository;

import org.example.clientkeeper.model.HistoriqueConnexion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HistoriqueConnexionRepository extends JpaRepository<HistoriqueConnexion, Long> {
}
