package com.health_donate.health.mapper;


import com.health_donate.health.dto.OngDTO;
import com.health_donate.health.entity.Ong;

public class OngMapper {

    public static OngDTO toDTO(Ong ong) {
        if (ong == null) return null;
        return OngDTO.builder()
                .id(ong.getId())
                .nom(ong.getNom())
                .typeOrganisation(ong.getTypeOrganisation())
                .descriptionMission(ong.getDescriptionMission())
                .logoUrl(ong.getLogoUrl())

                .emailContact(ong.getEmailContact())
                .telephoneContact(ong.getTelephoneContact())
                .siteWeb(ong.getSiteWeb())
                .adresse(ong.getAdresse())
                .ville(ong.getVille())
                .codePostal(ong.getCodePostal())
                .pays(ong.getPays())

                .nomCompletRepresentant(ong.getNomCompletRepresentant())
                .fonctionRepresentant(ong.getFonctionRepresentant())
                .emailRepresentant(ong.getEmailRepresentant())
                .telephoneRepresentant(ong.getTelephoneRepresentant())

                .numeroEnregistrement(ong.getNumeroEnregistrement())
                .dateCreation(ong.getDateCreation())
                .statut(ong.getStatut())
                .confirmationOfficielle(ong.isConfirmationOfficielle())
                .estActif(ong.isEstActif())
                .build();
    }

    public static Ong toEntity(OngDTO dto) {
        if (dto == null) return null;
        return Ong.builder()
                .id(dto.getId())
                .nom(dto.getNom())
                .typeOrganisation(dto.getTypeOrganisation())
                .descriptionMission(dto.getDescriptionMission())
                .logoUrl(dto.getLogoUrl())

                .emailContact(dto.getEmailContact())
                .telephoneContact(dto.getTelephoneContact())
                .siteWeb(dto.getSiteWeb())
                .adresse(dto.getAdresse())
                .ville(dto.getVille())
                .codePostal(dto.getCodePostal())
                .pays(dto.getPays())

                .nomCompletRepresentant(dto.getNomCompletRepresentant())
                .fonctionRepresentant(dto.getFonctionRepresentant())
                .emailRepresentant(dto.getEmailRepresentant())
                .telephoneRepresentant(dto.getTelephoneRepresentant())

                .numeroEnregistrement(dto.getNumeroEnregistrement())
                .dateCreation(dto.getDateCreation())
                .statut(dto.getStatut())
                .confirmationOfficielle(dto.isConfirmationOfficielle())
                .estActif(dto.isEstActif())
                .build();
    }
}