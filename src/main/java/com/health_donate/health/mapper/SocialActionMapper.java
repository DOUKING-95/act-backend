package com.health_donate.health.mapper;

import com.health_donate.health.dto.SocialActionDTO;
import com.health_donate.health.entity.SocialAction;
import com.health_donate.health.entity.Image;
import com.health_donate.health.entity.Participation;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class SocialActionMapper {

    public static SocialActionDTO toDTO(SocialAction action) {
        if (action == null) return null;

        SocialActionDTO dto = new SocialActionDTO();
        dto.setId(action.getId());
        dto.setTitre(action.getTitre());
        dto.setLieu(action.getLieu());
        dto.setDescription(action.getDescription());
        dto.setPassed(action.isPassed());
        dto.setBenevolNumber(action.getBenevolNumber());
        dto.setDateDebut(action.getDateDebut());
        dto.setDateFin(action.getDateFin());
        dto.setType(action.getType());
        dto.setHeureDebut(action.getHeureDebut() != null ? action.getHeureDebut() : "");
        dto.setHeureFin(action.getHeureFin() != null ? action.getHeureFin() : "");
        dto.setInfosSupplementaires(action.getInfosSupplementaires() != null ? action.getInfosSupplementaires() :"");

        dto.setAssociationId(action.getAssociation().getId());
        if (action.getImages() != null) {
            dto.setImageIds(action.getImages()
                    .stream()
                    .map(Image::getId)
                    .collect(Collectors.toList()));
        } else {
            dto.setImageIds(new ArrayList<>());
        }

        if (action.getParticipations() != null) {
            dto.setParticipationIds(action.getParticipations()
                    .stream()
                    .map(Participation::getId)
                    .collect(Collectors.toList()));
        } else {
            dto.setParticipationIds(new ArrayList<>());
        }

        return dto;
    }

    public static SocialAction toEntity(SocialActionDTO dto) {
        if (dto == null) return null;

        SocialAction action = new SocialAction();
        action.setId(dto.getId());
        action.setTitre(dto.getTitre());
        action.setLieu(dto.getLieu());
        action.setDescription(dto.getDescription());
        action.setPassed(dto.isPassed());
        action.setBenevolNumber(dto.getBenevolNumber());
        action.setDateDebut(dto.getDateDebut());
        action.setDateFin(dto.getDateFin());
        action.setHeureDebut(dto.getHeureDebut());
        action.setType(dto.getType());
        action.setDate(dto.getDate());
        action.setHeureFin(dto.getHeureFin());
        action.setInfosSupplementaires(dto.getInfosSupplementaires() != null ? dto.getInfosSupplementaires() : "");
action.setLongitude(dto.getLongitude());
action.setLatitude(dto.getLatitude());

        // Les listes d’IDs ne sont pas converties en entités ici pour éviter de charger la DB
        // Cela peut être fait dans le service si nécessaire
        return action;
    }
}
