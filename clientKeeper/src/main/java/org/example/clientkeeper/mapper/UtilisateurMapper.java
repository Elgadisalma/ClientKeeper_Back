package org.example.clientkeeper.mapper;

import org.example.clientkeeper.dto.UtilisateurDTO;
import org.example.clientkeeper.model.Utilisateur;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UtilisateurMapper {
    UtilisateurDTO toDTO(Utilisateur utilisateur);

    Utilisateur toEntity(UtilisateurDTO dto);

    List<UtilisateurDTO> toDTO(List<Utilisateur> utilisateurs);
}
