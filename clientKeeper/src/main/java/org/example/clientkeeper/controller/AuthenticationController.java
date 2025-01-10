package org.example.clientkeeper.controller;

import lombok.RequiredArgsConstructor;
import org.example.clientkeeper.dto.request.AuthenticationRequest;
import org.example.clientkeeper.dto.request.RegisterRequest;
import org.example.clientkeeper.dto.response.AuthenticationResponse;
import org.example.clientkeeper.service.AuthenticationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody RegisterRequest request
    ) {
        return ResponseEntity.ok(authenticationService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request) {
        AuthenticationResponse response = authenticationService.authenticate(request);
        return ResponseEntity.ok(response);
    }

}