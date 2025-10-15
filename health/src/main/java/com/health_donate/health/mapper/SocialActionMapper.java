package com.health_donate.health.mapper;


import com.health_donate.health.dto.SocialActionDTO;
import com.health_donate.health.entity.SocialAction;
import com.health_donate.health.entity.Image;
import com.health_donate.health.entity.Participation;

import java.util.List;
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

        if (action.getImages() != null) {
            dto.setImageIds(action.getImages()
                    .stream()
                    .map(Image::getId)
                    .collect(Collectors.toList()));
        }

        if (action.getParticipations() != null) {
            dto.setParticipationIds(action.getParticipations()
                    .stream()
                    .map(Participation::getId)
                    .collect(Collectors.toList()));
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

        // Les listes d’IDs ne sont pas converties ici pour éviter de charger des entités
        // Cela peut être fait dans le service si besoin
        return action;
    }
}

