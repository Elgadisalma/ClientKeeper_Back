package org.example.clientkeeper.service.impl;

import org.example.clientkeeper.dto.OffreDTO;
import org.example.clientkeeper.mapper.OffreMapper;
import org.example.clientkeeper.model.Offre;
import org.example.clientkeeper.repository.OffreRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OffreServiceImplTest {

    @InjectMocks
    private OffreServiceImpl offreService;

    @Mock
    private OffreRepository offreRepository;

    @Mock
    private OffreMapper offreMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddOffre_Success() {
        OffreDTO offreDTO = new OffreDTO();
        offreDTO.setTaux(5.0);
        offreDTO.setDescription("Offre spéciale");
        offreDTO.setDateExpiration(LocalDate.now().plusDays(30));

        Offre offre = new Offre();
        offre.setTaux(5.0);
        offre.setDescription("Offre spéciale");
        offre.setDateExpiration(LocalDate.now().plusDays(30));

        when(offreMapper.toEntity(offreDTO)).thenReturn(offre);
        when(offreRepository.save(offre)).thenReturn(offre);
        when(offreMapper.toDTO(offre)).thenReturn(offreDTO);

        OffreDTO result = offreService.addOffre(offreDTO);

        assertNotNull(result);
        assertEquals(5.0, result.getTaux());
        assertEquals("Offre spéciale", result.getDescription());
        verify(offreRepository, times(1)).save(offre);
    }

    @Test
    void testAddOffre_InvalidDate() {
        OffreDTO offreDTO = new OffreDTO();
        offreDTO.setDateExpiration(LocalDate.now().minusDays(1)); // Date expirée

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            offreService.addOffre(offreDTO);
        });

        assertEquals("La date d'expiration doit être après aujourd'hui.", exception.getMessage());
    }

    @Test
    void testDeleteOffre() {
        Long offreId = 1L;

        doNothing().when(offreRepository).deleteById(offreId);

        offreService.deleteOffre(offreId);

        verify(offreRepository, times(1)).deleteById(offreId);
    }

    @Test
    void testGetAllOffres() {
        Offre offre1 = new Offre();
        Offre offre2 = new Offre();

        OffreDTO dto1 = new OffreDTO();
        OffreDTO dto2 = new OffreDTO();

        when(offreRepository.findAll()).thenReturn(List.of(offre1, offre2));
        when(offreMapper.toDTO(List.of(offre1, offre2))).thenReturn(List.of(dto1, dto2));

        List<OffreDTO> result = offreService.getAllOffres();

        assertEquals(2, result.size());
        verify(offreRepository, times(1)).findAll();
    }

    @Test
    void testGetOffreById_Success() {
        Long offreId = 1L;
        Offre offre = new Offre();
        OffreDTO offreDTO = new OffreDTO();

        when(offreRepository.findById(offreId)).thenReturn(Optional.of(offre));
        when(offreMapper.toDTO(offre)).thenReturn(offreDTO);

        Optional<OffreDTO> result = offreService.getOffreById(offreId);

        assertTrue(result.isPresent());
        verify(offreRepository, times(1)).findById(offreId);
    }

    @Test
    void testGetOffreById_NotFound() {
        Long offreId = 1L;

        when(offreRepository.findById(offreId)).thenReturn(Optional.empty());

        Optional<OffreDTO> result = offreService.getOffreById(offreId);

        assertFalse(result.isPresent());
        verify(offreRepository, times(1)).findById(offreId);
    }

    @Test
    void testUpdateOffre_Success() {
        Long offreId = 1L;
        OffreDTO offreDTO = new OffreDTO();
        offreDTO.setTaux(7.0);
        offreDTO.setDescription("Nouvelle offre");
        offreDTO.setDateExpiration(LocalDate.now().plusDays(40));

        Offre existingOffre = new Offre();
        existingOffre.setId(offreId);

        when(offreRepository.findById(offreId)).thenReturn(Optional.of(existingOffre));
        when(offreRepository.save(any(Offre.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(offreMapper.toDTO(any(Offre.class))).thenReturn(offreDTO);

        Optional<OffreDTO> result = offreService.updateOffre(offreId, offreDTO);

        assertTrue(result.isPresent());
        assertEquals(7.0, result.get().getTaux());
        assertEquals("Nouvelle offre", result.get().getDescription());
        verify(offreRepository, times(1)).save(any(Offre.class));
    }

    @Test
    void testUpdateOffre_InvalidDate() {
        OffreDTO offreDTO = new OffreDTO();
        offreDTO.setDateExpiration(LocalDate.now().minusDays(1));

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            offreService.updateOffre(1L, offreDTO);
        });

        assertEquals("La date d'expiration doit être après aujourd'hui.", exception.getMessage());
    }

    @Test
    void testUpdateOffre_NotFound() {
        Long offreId = 1L;
        OffreDTO offreDTO = new OffreDTO();
        offreDTO.setDateExpiration(LocalDate.now().plusDays(10));

        when(offreRepository.findById(offreId)).thenReturn(Optional.empty());

        Optional<OffreDTO> result = offreService.updateOffre(offreId, offreDTO);

        assertFalse(result.isPresent());
        verify(offreRepository, never()).save(any(Offre.class));
    }
}
