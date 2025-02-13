package org.example.clientkeeper.controller;

import org.example.clientkeeper.dto.OffreDTO;
import org.example.clientkeeper.service.OffreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/offres")
public class OffreController {

    @Autowired
    private OffreService offreService;

    @PostMapping
    public ResponseEntity<OffreDTO> addOffre(@RequestBody OffreDTO offreDTO) {
        OffreDTO createdOffre = offreService.addOffre(offreDTO);
        return ResponseEntity.ok(createdOffre);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOffre(@PathVariable Long id) {
        offreService.deleteOffre(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<OffreDTO>> getAllOffres() {
        List<OffreDTO> offres = offreService.getAllOffres();
        return ResponseEntity.ok(offres);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OffreDTO> getOffreById(@PathVariable Long id) {
        Optional<OffreDTO> offreDTO = offreService.getOffreById(id);
        return offreDTO.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
}
