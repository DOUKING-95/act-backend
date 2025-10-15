package com.health_donate.health.mapper;



import com.health_donate.health.dto.ParticipationDTO;
import com.health_donate.health.entity.Actor;
import com.health_donate.health.entity.Participation;
import com.health_donate.health.entity.SocialAction;

public class ParticipationMapper {

    public static ParticipationDTO toDTO(Participation participation) {
        if (participation == null) return null;

        ParticipationDTO dto = new ParticipationDTO();
        dto.setId(participation.getId());
        dto.setActeurId(
                participation.getActeur() != null ? participation.getActeur().getId() : null
        );
        dto.setActiviteId(
                participation.getActivite() != null ? participation.getActivite().getId() : null
        );
        return dto;
    }

    public static Participation toEntity(ParticipationDTO dto, Actor acteur, SocialAction activite) {
        if (dto == null) return null;

        Participation participation = new Participation();
        participation.setId(dto.getId());
        participation.setActeur(acteur);
        participation.setActivite(activite);
        return participation;
    }
}

