package com.health_donate.health.mapper;

import com.health_donate.health.dto.AssociationDTO;
import com.health_donate.health.entity.Association;
import com.health_donate.health.entity.SocialAction;

import java.util.Date;
import java.util.stream.Collectors;

public class AssociationMapper {

    public static AssociationDTO toDTO(Association association) {
        if (association == null) return null;

        AssociationDTO dto = new AssociationDTO();
        dto.setId(association.getId());
        dto.setName(association.getName());
        dto.setAddress(association.getAddress());
        dto.setPhone(association.getPhone());
        dto.setEmail(association.getEmail());
        dto.setLogoUrl(association.getLogoUrl());
        dto.setCovertUrl(association.getCovertUrl());
        dto.setActive(association.isActive());
        dto.setDescription(association.getDescription());
        dto.setCategorie(association.getCategorie());
        dto.setStatut(association.getStatut());
        dto.setDateCreation(association.getDateCreation());
        dto.setTypeAssociation(association.getTypeAssociation());
        dto.setSiteWeb(association.getSiteWeb());
        dto.setVille(association.getVille());
        dto.setCodePostal(association.getCodePostal());
        dto.setPays(association.getPays());
        dto.setNomComplet(association.getNomComplet());
        dto.setFonction(association.getFonction());
        dto.setNumeroEnregistrement(association.getNumeroEnregistrement());
        dto.setConfirmationOfficielle(association.getConfirmationOfficielle());

        if (association.getUser() != null) {
            dto.setUserId(association.getUser().getId());
        }


        if (association.getSocialActions() != null) {
            dto.setSocialActionIds(
                    association.getSocialActions()
                            .stream()
                            .map(SocialAction::getId)
                            .collect(Collectors.toList())
            );
        }

        return dto;
    }

    public static Association toEntity(AssociationDTO dto) {
        if (dto == null) return null;

        Association association = new Association();
        association.setId(dto.getId());
        association.setName(dto.getName());
        association.setAddress(dto.getAddress());
        association.setPhone(dto.getPhone());
        association.setEmail(dto.getEmail());
        association.setLogoUrl(dto.getLogoUrl());
        association.setCovertUrl(dto.getCovertUrl());
        association.setActive(dto.getActive());
        association.setDescription(dto.getDescription());
        association.setCategorie(dto.getCategorie());
        association.setStatut(dto.getStatut());
        association.setDateCreation(dto.getDateCreation() != null ? dto.getDateCreation() : new Date());
        association.setTypeAssociation(dto.getTypeAssociation());
        association.setSiteWeb(dto.getSiteWeb());
        association.setVille(dto.getVille());
        association.setCodePostal(dto.getCodePostal());
        association.setPays(dto.getPays());
        association.setNomComplet(dto.getNomComplet());
        association.setFonction(dto.getFonction());
        association.setNumeroEnregistrement(dto.getNumeroEnregistrement());
        association.setConfirmationOfficielle(dto.getConfirmationOfficielle());



        return association;
    }
}
