package org.example.clientkeeper.mapper;

import org.example.clientkeeper.dto.OffreDTO;
import org.example.clientkeeper.model.Offre;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OffreMapper {
    OffreDTO toDTO(Offre offre);

    Offre toEntity(OffreDTO dto);

    List<OffreDTO> toDTO(List<Offre> offres);
}
