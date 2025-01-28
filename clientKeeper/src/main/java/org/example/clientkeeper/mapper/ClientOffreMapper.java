package org.example.clientkeeper.mapper;

import org.example.clientkeeper.dto.ClientOffreDTO;
import org.example.clientkeeper.model.ClientOffre;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ClientOffreMapper {
    ClientOffreDTO toDTO(ClientOffre client);

    ClientOffre toEntity(ClientOffreDTO dto);

    List<ClientOffreDTO> toDTO(List<ClientOffre> clients);
}
