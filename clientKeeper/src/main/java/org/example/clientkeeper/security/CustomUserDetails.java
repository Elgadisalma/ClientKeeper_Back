package org.example.clientkeeper.security;

import lombok.Getter;
import org.example.clientkeeper.model.Utilisateur;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

@Getter
public class CustomUserDetails extends User {

    private final Long utilisateurId;

    public CustomUserDetails(Utilisateur utilisateur, Collection<? extends GrantedAuthority> authorities, Long userId) {
        super(utilisateur.getEmail(), utilisateur.getPassword(), authorities);
        this.utilisateurId = utilisateur.getId();

    }
}
