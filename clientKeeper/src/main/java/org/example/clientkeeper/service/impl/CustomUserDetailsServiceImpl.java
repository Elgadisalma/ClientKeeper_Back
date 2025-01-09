package org.example.clientkeeper.service.impl;


import org.example.clientkeeper.model.Utilisateur;
import org.example.clientkeeper.repository.UtilisateurRepository;
import org.example.clientkeeper.security.CustomUserDetails;
import org.example.clientkeeper.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;


@Service
public class CustomUserDetailsServiceImpl implements CustomUserDetailsService {

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    @Override
    public UserDetails loadUserByEmail(String email) throws UsernameNotFoundException {
        Utilisateur utilisateur = utilisateurRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Utilisateur non trouvé avec l'email: " + email));

        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(utilisateur.getRole().name());

        return new CustomUserDetails(
                utilisateur,
                Collections.singletonList(authority),
                utilisateur.getId()
        );
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return loadUserByEmail(username);
    }
}