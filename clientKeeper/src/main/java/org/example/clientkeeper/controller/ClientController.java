package org.example.clientkeeper.controller;

import org.example.clientkeeper.dto.ClientDTO;
import org.example.clientkeeper.dto.ClientOffreDTO;
import org.example.clientkeeper.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clients")
public class ClientController {

    @Autowired
    private ClientService clientService;

    @GetMapping("/{id}")
    public ResponseEntity<ClientDTO> getClientById(@PathVariable Long id) {
        ClientDTO client = clientService.getClientById(id);
        return ResponseEntity.ok(client);
    }

    @GetMapping("/noAppr")
    public ResponseEntity<List<ClientDTO>> getNoAppClients() {
        List<ClientDTO> clients = clientService.getNoAppClients();
        return ResponseEntity.ok(clients);
    }

    @PostMapping("/associate")
    public ResponseEntity<String> associateClientWithOffre(@RequestBody ClientOffreDTO clientOffreDTO) {
        try {
            clientService.associateClientWithOffre(clientOffreDTO);
            return ResponseEntity.ok("Offre associée au client avec succès.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/approveClient/{clientId}")
    public ResponseEntity<String> approveClient(@PathVariable Long clientId) {
        clientService.approveClient(clientId);
        return ResponseEntity.ok("Client approuvé avec succès");
    }
}
