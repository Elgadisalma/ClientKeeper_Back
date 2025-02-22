package org.example.clientkeeper.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ApproveClientRequest {
    private Long clientId;
    private String newNumeroCompte;
}
