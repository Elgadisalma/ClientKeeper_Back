package org.example.clientkeeper.mapper;

import org.example.clientkeeper.dto.AiModelDTO;
import org.example.clientkeeper.model.AiModel;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AiModelMapper {
    AiModelDTO toDTO(AiModel aiModel);

    AiModel toEntity(AiModelDTO dto);

    List<AiModelDTO> toDTO(List<AiModel> aiModels);
}
