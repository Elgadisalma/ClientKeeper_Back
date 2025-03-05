package org.example.clientkeeper.service;

import org.example.clientkeeper.dto.OffreDTO;

import java.util.List;
import java.util.Optional;

public interface OffreService {
    OffreDTO addOffre(OffreDTO offreDTO);
    void deleteOffre(Long id);
    List<OffreDTO> getAllOffres();
    Optional<OffreDTO> getOffreById(Long id);

//    Optional<OffreDTO> updateOffre(OffreDTO offreDTO);

    Optional<OffreDTO> updateOffre(Long id, OffreDTO offreDTO);
}
