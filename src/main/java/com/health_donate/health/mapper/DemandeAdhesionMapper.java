package com.health_donate.health.mapper;

import com.health_donate.health.dto.DemandeAdhesionDTO;
import com.health_donate.health.entity.Actor;
import com.health_donate.health.entity.Association;
import com.health_donate.health.entity.DemandeAdhesion;

public class DemandeAdhesionMapper {

    public static DemandeAdhesionDTO toDTO(DemandeAdhesion demande) {
        if (demande == null) return null;

        DemandeAdhesionDTO dto = new DemandeAdhesionDTO();
        dto.setId(demande.getId());
        dto.setUserId(demande.getUser().getId());
        dto.setAssociationId(demande.getAssociation().getId());
        dto.setMessage(demande.getMessage());
        dto.setStatut(demande.getStatut().name());
        dto.setDateDemande(demande.getDateDemande());
        dto.setUser(ActorMapper.toDTO(demande.getUser()));
        dto.setAssociation(AssociationMapper.toDTO(demande.getAssociation()));

        return dto;
    }

    public static DemandeAdhesion toEntity(DemandeAdhesionDTO dto, Actor user, Association association) {
        DemandeAdhesion demande = new DemandeAdhesion();
        demande.setUser(user);
        demande.setAssociation(association);
        demande.setMessage(dto.getMessage());
        return demande;
    }
}
