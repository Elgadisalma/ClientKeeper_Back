package org.example.clientkeeper.repository;

import org.example.clientkeeper.model.Client;
import org.example.clientkeeper.model.ClientOffre;
import org.example.clientkeeper.model.ClientOffreId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientOffreRepository extends JpaRepository<ClientOffre, ClientOffreId> {
}
