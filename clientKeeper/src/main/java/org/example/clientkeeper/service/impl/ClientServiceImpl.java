package org.example.clientkeeper.service.impl;

import org.example.clientkeeper.dto.ClientDTO;
import org.example.clientkeeper.dto.ClientOffreDTO;
import org.example.clientkeeper.exception.CustomValidationException;
import org.example.clientkeeper.mapper.ClientMapper;
import org.example.clientkeeper.mapper.ClientOffreMapper;
import org.example.clientkeeper.model.*;
import org.example.clientkeeper.repository.ClientOffreRepository;
import org.example.clientkeeper.repository.ClientRepository;
import org.example.clientkeeper.repository.OffreRepository;
import org.example.clientkeeper.repository.UtilisateurRepository;
import org.example.clientkeeper.service.ClientService;
import org.example.clientkeeper.service.EmailSenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ClientServiceImpl implements ClientService {

    @Autowired
    ClientMapper clientMapper;

    @Autowired
    ClientRepository clientRepository;

    @Autowired
    UtilisateurRepository utilisateurRepository;

    @Autowired
    OffreRepository offreRepository;

    @Autowired
    ClientOffreMapper clientOffreMapper;

    @Autowired
    ClientOffreRepository clientOffreRepository;

    @Autowired
    private EmailSenderService emailService;


    @Override
    public ClientDTO getClientById(Long id) {
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new CustomValidationException("Client introuvable avec l'ID : " + id));
        return clientMapper.toDTO(client);
    }

    @Override
    public void approveClient(Long clientId, String newNumeroCompte) {
        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new CustomValidationException("Client non trouvé"));

        if (client.getStatus() == 0) {
            throw new CustomValidationException("Client déjà approuvé");
        }

        client.setStatus(0);
        client.setNumeroCompte(newNumeroCompte);
        clientRepository.save(client);

        String subject = "Félicitations ! Vous êtes approuvé";
        String message = "Cher " + client.getNom() + ",\n\n" +
                "Félicitations ! Vous êtes maintenant approuvé en tant que client de ClientKeeper.\n" +
                "Voici votre nouveau numéro de compte : " + newNumeroCompte + "\n\n" +
                "Merci de faire confiance à notre banque.\n\n" +
                "Cordialement,\nL'équipe ClientKeeper.";

        emailService.sendVerificationEmail(client.getEmail(), subject, message);
    }


    @Override
    public void associateClientWithOffre(ClientOffreDTO clientOffreDTO) {
        Client client = clientRepository.findById(clientOffreDTO.getClientId())
                .orElseThrow(() -> new IllegalArgumentException("Client not found"));

        Offre offre = offreRepository.findById(clientOffreDTO.getOffreId())
                .orElseThrow(() -> new IllegalArgumentException("Offre not found with ID"));

        ClientOffreId clientOffreId = new ClientOffreId();
        clientOffreId.setClientId(clientOffreDTO.getClientId());
        clientOffreId.setOffreId(clientOffreDTO.getOffreId());

        ClientOffre clientOffre = clientOffreRepository.findById(clientOffreId).orElse(null);

        if (clientOffre != null) {
            clientOffre.setDateAffectation(LocalDate.now());
        } else {
            clientOffre = new ClientOffre();
            clientOffre.setId(clientOffreId);
            clientOffre.setClient(client);
            clientOffre.setOffre(offre);
            clientOffre.setDateAffectation(LocalDate.now());
        }

        clientOffreRepository.save(clientOffre);

        double taux = offre.getTaux();
        String clientEmail = client.getEmail();
        String subject = "Nouvelle Offre Associée";
        String message = String.format("Bonjour %s,\n\n" +
                        "Vous avez été associé à l'offre suivante le : %s.\n\n" +
                        "Elle sera expirée le : %s.\n\n" +
                        "Le pourcentage d'affectation est de : %.2f%%.\n\n" +
                        "Merci de votre confiance.\n\n" +
                        "Cordialement,\nL'équipe ClientKeeper.",
                client.getNom(), offre.getDateExpiration(), offre.getDateExpiration(), taux);

        emailService.sendVerificationEmail(clientEmail, subject, message);
    }



    @Override
    public List<ClientDTO> getNoAppClients() {
        List<Client> clients = clientRepository.findByStatus(1);
        return clientMapper.toDTO(clients);
    }


    @Override
    public void deleteClient(Long clientId) {
        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new CustomValidationException("Client non trouvé avec l'ID : " + clientId));

        if (client.getStatus() != 1) {
            throw new CustomValidationException("Impossible de supprimer un client déjà approuvé !");
        }

        Utilisateur utilisateur = utilisateurRepository.findById(clientId)
                .orElseThrow(() -> new CustomValidationException("Utilisateur non trouvé avec l'ID : " + clientId));

        clientRepository.delete(client);

        utilisateurRepository.delete(utilisateur);
    }

    @Override
    public ClientDTO getClientByEmail(String email) {
        Utilisateur utilisateur = utilisateurRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Utilisateur non trouvé"));

        if (utilisateur instanceof Client) {
            Client client = (Client) utilisateur;
            return clientMapper.toDTO(client);
        }

        throw new CustomValidationException("L'utilisateur n'est pas un client.");
    }


    public boolean hasActiveStatus(String email) {
        return clientRepository.findByEmail(email)
                .map(client -> client.getStatus() == 0)
                .orElse(false);
    }



}
