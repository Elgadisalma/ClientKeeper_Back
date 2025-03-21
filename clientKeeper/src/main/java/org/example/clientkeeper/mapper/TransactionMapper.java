package org.example.clientkeeper.mapper;

import org.example.clientkeeper.dto.TransactionDTO;
import org.example.clientkeeper.dto.TransactionDetailsDTO;
import org.example.clientkeeper.model.Transaction;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TransactionMapper {

    @Mapping(source = "sender.nom", target = "senderName")
    @Mapping(source = "sender.prenom", target = "senderPrenom")
    @Mapping(source = "sender.numeroCompte", target = "senderAccountNumber")
    @Mapping(source = "receiver.nom", target = "receiverName")
    @Mapping(source = "receiver.prenom", target = "receiverPrenom")
    @Mapping(source = "receiver.numeroCompte", target = "receiverAccountNumber")
    TransactionDetailsDTO toDetailsDTO(Transaction transaction);

    @Mapping(source = "receiver.nom", target = "receiverName")
    @Mapping(source = "receiver.prenom", target = "receiverPrenom")
    @Mapping(source = "receiver.numeroCompte", target = "receiverAccountNumber")
    TransactionDTO toDTO(Transaction transaction);

    Transaction toEntity(TransactionDTO dto);

    List<TransactionDTO> toDTO(List<Transaction> transactions);
}
