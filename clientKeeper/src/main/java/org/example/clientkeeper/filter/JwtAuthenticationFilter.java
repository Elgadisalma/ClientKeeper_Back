package org.example.clientkeeper.filter;


import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.example.clientkeeper.service.CustomUserDetailsService;
import org.example.clientkeeper.util.JwtUtil;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@AllArgsConstructor
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private JwtUtil jwtUtil;
    private CustomUserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain chain) throws ServletException, IOException {
        try {
            String path = request.getRequestURI();
            System.out.println("Processing request for path: " + path);
            // Ignore JWT validation for public endpoints
            boolean shouldNotFilter = shouldNotFilter(request);
            System.out.println("Should not filter: " + shouldNotFilter);
            if (shouldNotFilter(request)) {
                System.out.println("Skipping JWT filter for path: " + path);
                chain.doFilter(request, response);
                return;
            }

            String authHeader = request.getHeader("Authorization");
            if (authHeader != null && authHeader.startsWith("Bearer ")) {
                String token = authHeader.substring(7);
                if (jwtUtil.validateToken(token)) {
                    String email = jwtUtil.getEmailFromToken(token);
                    UserDetails userDetails = userDetailsService.loadUserByEmail(email);
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities());
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
            chain.doFilter(request, response);
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json");
            response.getWriter().write("{\"error\": \"" + e.getMessage() + "\"}");
        }
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getRequestURI();
        boolean isExcluded = path.startsWith("/api/auth/register") || path.startsWith("/api/auth/login");
        System.out.println("Path: " + path + ", Should Not Filter: " + isExcluded);
        return isExcluded;
    }

}
