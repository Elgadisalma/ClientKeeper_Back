package org.example.clientkeeper.mapper;

import org.example.clientkeeper.dto.TransactionDTO;
import org.example.clientkeeper.model.Transaction;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TransactionMapper {
    TransactionDTO toDTO(Transaction transaction);

    Transaction toEntity(TransactionDTO dto);

    List<TransactionDTO> toDTO(List<Transaction> transactions);
}
