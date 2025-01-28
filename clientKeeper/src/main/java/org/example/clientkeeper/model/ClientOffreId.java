package org.example.clientkeeper.model;

import lombok.Data;
import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

@Data
@Embeddable
public class ClientOffreId implements Serializable {

    private Long clientId;
    private Long offreId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ClientOffreId)) return false;
        ClientOffreId that = (ClientOffreId) o;
        return Objects.equals(clientId, that.clientId) && Objects.equals(offreId, that.offreId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(clientId, offreId);
    }
}
