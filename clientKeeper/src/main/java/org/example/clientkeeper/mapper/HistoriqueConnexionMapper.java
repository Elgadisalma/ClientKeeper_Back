package org.example.clientkeeper.mapper;

import org.example.clientkeeper.dto.HistoriqueConnexionDTO;
import org.example.clientkeeper.model.HistoriqueConnexion;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface HistoriqueConnexionMapper {
    HistoriqueConnexionDTO toDTO(HistoriqueConnexion historiqueConnexion);

    HistoriqueConnexion toEntity(HistoriqueConnexionDTO dto);

    List<HistoriqueConnexionDTO> toDTO(List<HistoriqueConnexion> historiques);
}
