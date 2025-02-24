package org.example.clientkeeper.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.clientkeeper.model.Client;
import org.example.clientkeeper.repository.ClientRepository;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;

@Component
public class CustomAccessFilter extends OncePerRequestFilter {

    private final ClientRepository clientRepository;

    public CustomAccessFilter(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getServletPath();
        return path.equals("/api/auth/register") ||
                path.equals("/api/auth/forgot-password");
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String path = request.getServletPath();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // Traitement spécial pour /api/auth/login
        if (path.equals("/api/auth/login")) {
            String email = extractEmailFromLoginRequest(request);

            if (email != null) {
                Optional<Client> clientOpt = clientRepository.findByEmail(email);
                if (clientOpt.isPresent()) {
                    Client client = clientOpt.get();
                    if (client.getStatus() != 0 && !client.getRole().equals("ROLE_ADMIN")) {
                        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                        response.getWriter().write("Accès refusé : Seuls les clients approuvés et les administrateurs peuvent se connecter.");
                        return;
                    }
                }
            }
        }
        else if (authentication != null && authentication.isAuthenticated()) {
            String email = authentication.getName();
            Optional<Client> clientOpt = clientRepository.findByEmail(email);

            if (clientOpt.isPresent()) {
                Client client = clientOpt.get();
                if (client.getStatus() != 0 && !client.getRole().equals("ROLE_ADMIN")) {
                    throw new AccessDeniedException("Seuls les clients approuves et les administrateurs peuvent se connecter.");
                }
            }
        }

        filterChain.doFilter(request, response);
    }

    private String extractEmailFromLoginRequest(HttpServletRequest request) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            Map<String, String> requestBody = mapper.readValue(request.getInputStream(), Map.class);
            return requestBody.get("email");
        } catch (IOException e) {
            return null;
        }
    }
}
