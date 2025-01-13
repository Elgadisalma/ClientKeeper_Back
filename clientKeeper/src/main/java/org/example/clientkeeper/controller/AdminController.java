package org.example.clientkeeper.controller;

import org.example.clientkeeper.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    ClientService clientService;

    @PutMapping("/approveClient/{clientId}")
    public ResponseEntity<String> approveClient(@PathVariable Long clientId) {
        clientService.approveClient(clientId);
        return ResponseEntity.ok("Client approuvé avec succès");
    }
}
