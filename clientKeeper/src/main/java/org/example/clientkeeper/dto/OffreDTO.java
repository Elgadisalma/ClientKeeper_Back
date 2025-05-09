package org.example.clientkeeper.dto;

import lombok.Data;
import java.time.LocalDate;

@Data
public class OffreDTO {
    private Long id;
    private double taux;
    private LocalDate dateCreation;
    private LocalDate dateExpiration;
    private String description;
}
