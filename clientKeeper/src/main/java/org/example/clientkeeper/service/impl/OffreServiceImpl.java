package org.example.clientkeeper.service.impl;

import org.example.clientkeeper.dto.OffreDTO;
import org.example.clientkeeper.mapper.OffreMapper;
import org.example.clientkeeper.model.Offre;
import org.example.clientkeeper.repository.OffreRepository;
import org.example.clientkeeper.service.OffreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class OffreServiceImpl implements OffreService {

    @Autowired
    private OffreRepository offreRepository;

    @Autowired
    private OffreMapper offreMapper;

    @Override
    public OffreDTO addOffre(OffreDTO offreDTO) {
        if (offreDTO.getDateExpiration() == null || offreDTO.getDateExpiration().isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("La date d'expiration doit être après aujourd'hui.");
        }

        Offre offre = offreMapper.toEntity(offreDTO);
        offre.setDateCreation(LocalDate.now());
        Offre savedOffre = offreRepository.save(offre);

        return offreMapper.toDTO(savedOffre);
    }

    @Override
    public void deleteOffre(Long id) {
        offreRepository.deleteById(id);
    }

    @Override
    public List<OffreDTO> getAllOffres() {
        List<Offre> offres = offreRepository.findAll();
        return offreMapper.toDTO(offres);
    }

    @Override
    public Optional<OffreDTO> getOffreById(Long id) {
        Optional<Offre> offre = offreRepository.findById(id);

        return offre.map(offreMapper::toDTO);
    }

    @Override
    public Optional<OffreDTO> updateOffre(Long id, OffreDTO offreDTO) {
        if (offreDTO.getDateExpiration() == null || offreDTO.getDateExpiration().isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("La date d'expiration doit être après aujourd'hui.");
        }

        Optional<Offre> existingOffre = offreRepository.findById(id);

        if (existingOffre.isPresent()) {
            Offre offreToUpdate = existingOffre.get();
            offreToUpdate.setTaux(offreDTO.getTaux());
            offreToUpdate.setDescription(offreDTO.getDescription());
            offreToUpdate.setDateExpiration(offreDTO.getDateExpiration());

            Offre updatedOffre = offreRepository.save(offreToUpdate);
            return Optional.of(offreMapper.toDTO(updatedOffre));
        } else {
            return Optional.empty();
        }
    }

}
