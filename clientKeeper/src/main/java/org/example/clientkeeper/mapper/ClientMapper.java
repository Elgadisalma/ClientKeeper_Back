package org.example.clientkeeper.mapper;

import org.example.clientkeeper.dto.ClientDTO;
import org.example.clientkeeper.model.Client;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ClientMapper {
    ClientDTO toDTO(Client client);

    Client toEntity(ClientDTO dto);

    List<ClientDTO> toDTO(List<Client> clients);
}
