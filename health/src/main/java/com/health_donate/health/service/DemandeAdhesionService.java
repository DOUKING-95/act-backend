package com.health_donate.health.service;

import com.health_donate.health.dto.DemandeAdhesionDTO;
import com.health_donate.health.entity.*;
import com.health_donate.health.enumT.StatutDemande;
import com.health_donate.health.mapper.DemandeAdhesionMapper;
import com.health_donate.health.repository.AssociationRepository;
import com.health_donate.health.repository.DemandeAdhesionRepository;
import com.health_donate.health.repository.MembreRepository;
import com.health_donate.health.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DemandeAdhesionService {

    private final DemandeAdhesionRepository demandeAdhesionRepository;
    private final AssociationRepository associationRepository;
    private final UserRepository userRepository;
    private final MembreRepository membreRepository;

    //Faire une demande d’adhésion
    public DemandeAdhesionDTO faireDemande(Long userId, Long associationId, String message) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("utilisateur introuvable"));
        Association association = associationRepository.findById(associationId)
                .orElseThrow(() -> new EntityNotFoundException("Association introuvable"));

        // Vérifie s'il a déjà fait une demande
        Optional<DemandeAdhesion> existing = demandeAdhesionRepository.findByUserAndAssociation(user, association);
        if (existing.isPresent()) {
            throw new RuntimeException("Vous avez déjà une demande en cours ou traitée.");
        }

        DemandeAdhesion demande = new DemandeAdhesion();
        demande.setUser((Actor) user);
        demande.setAssociation(association);
        demande.setMessage(message);
        demande.setStatut(StatutDemande.EN_ATTENTE);

        DemandeAdhesion saved = demandeAdhesionRepository.save(demande);
        return DemandeAdhesionMapper.toDTO(saved);
    }

    //Liste des demandes en attente pour une association
    public List<DemandeAdhesionDTO> demandesEnAttente(Long associationId) {
        Association association = associationRepository.findById(associationId)
                .orElseThrow(() -> new EntityNotFoundException("Association introuvable"));
        List<DemandeAdhesion> demandes = demandeAdhesionRepository.findByAssociationAndStatut(association, StatutDemande.EN_ATTENTE);
        return demandes.stream().map(DemandeAdhesionMapper::toDTO).collect(Collectors.toList());
    }

    //Accepter une demande
    public DemandeAdhesionDTO accepterDemande(Long demandeId) {
        DemandeAdhesion demande = demandeAdhesionRepository.findById(demandeId)
                .orElseThrow(() -> new EntityNotFoundException("Demande introuvable"));

        demande.setStatut(StatutDemande.ACCEPTEE);
        demandeAdhesionRepository.save(demande);

        // Ajoute le membre
        Membre membre = new Membre();
        membre.setUser(demande.getUser());
        membre.setAssociation(demande.getAssociation());
        membre.setActif(true);
        membreRepository.save(membre);

        return DemandeAdhesionMapper.toDTO(demande);
    }

    //Rejeter une demande
    public DemandeAdhesionDTO rejeterDemande(Long demandeId) {
        DemandeAdhesion demande = demandeAdhesionRepository.findById(demandeId)
                .orElseThrow(() -> new EntityNotFoundException("Demande introuvable"));
        demande.setStatut(StatutDemande.REJETEE);
        demandeAdhesionRepository.save(demande);
        return DemandeAdhesionMapper.toDTO(demande);
    }


}
