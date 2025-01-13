package org.example.clientkeeper.repository;

import org.example.clientkeeper.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
    Optional<Client> findByCin(String cin);
    Optional<Client> findByNomAndPrenomAndNumeroCompte(String nom, String prenom,String numeroCompte);

    Optional<Client> findByEmail(String senderEmail);
}
