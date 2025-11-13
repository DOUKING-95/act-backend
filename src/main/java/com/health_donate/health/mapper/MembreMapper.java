package com.health_donate.health.mapper;

import com.health_donate.health.dto.MembreDTO;
import com.health_donate.health.dto.ReceptionDTO;
import com.health_donate.health.entity.Association;
import com.health_donate.health.entity.Membre;
import com.health_donate.health.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class MembreMapper {

    public static MembreDTO toDTO(Membre entity) {
        if (entity == null) return null;

        MembreDTO dto = new MembreDTO();
        dto.setId(entity.getId());
        dto.setActif(entity.isActif());
        dto.setAssociation(AssociationMapper.toDTO(entity.getAssociation()));

        if (entity.getUser() != null) {
            dto.setUserId(entity.getUser().getId());
            dto.setUserName(entity.getUser().getName());
        }

        if (entity.getReceptions() != null) {
            dto.setReceptions(
                    entity.getReceptions().stream()
                            .map(reception -> {
                                ReceptionDTO rDto = new ReceptionDTO();
                                rDto.setId(reception.getId());
                                rDto.setEstLu(reception.getEstLu());
                                return rDto;
                            })
                            .collect(Collectors.toList())
            );
        }

        return dto;
    }

    public static Membre toEntity(MembreDTO dto, User user, Association association) {
        if (dto == null) return null;

        Membre entity = new Membre();
        entity.setId(dto.getId());
        entity.setActif(dto.getActif());
        entity.setUser(user);
        entity.setAssociation(association);

        return entity;
    }
}
