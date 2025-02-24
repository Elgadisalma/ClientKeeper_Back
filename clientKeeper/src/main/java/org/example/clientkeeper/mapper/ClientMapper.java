package org.example.clientkeeper.mapper;

import org.example.clientkeeper.dto.ClientDTO;
import org.example.clientkeeper.model.Client;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ClientMapper {
    @Mapping(source = "id", target = "id")
    ClientDTO toDTO(Client client);

    Client toEntity(ClientDTO dto);

    List<ClientDTO> toDTO(List<Client> clients);
}
