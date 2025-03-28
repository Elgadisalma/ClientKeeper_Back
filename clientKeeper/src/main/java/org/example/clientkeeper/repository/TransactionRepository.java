package org.example.clientkeeper.repository;

import org.example.clientkeeper.model.Client;
import org.example.clientkeeper.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findBySender(Client sender);

    int countBySenderAndDateTransactionAfter(Client sender, LocalDateTime date);

    List<Transaction> findByReceiver(Client client);
}
