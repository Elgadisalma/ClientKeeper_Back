package org.example.clientkeeper.mapper;

import org.example.clientkeeper.dto.ChurnPredictionDTO;
import org.example.clientkeeper.model.ChurnPrediction;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ChurnPredictionMapper {
    ChurnPredictionDTO toDTO(ChurnPrediction churnPrediction);

    ChurnPrediction toEntity(ChurnPredictionDTO dto);

    List<ChurnPredictionDTO> toDTO(List<ChurnPrediction> churnPredictions);
}
