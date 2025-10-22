package com.health_donate.health.mapper;

import com.health_donate.health.dto.MembreDTO;
import com.health_donate.health.entity.Membre;
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
        dto.setAssociations(AssociationMapper.toDTO(entity.getAssociation()));
        dto.setUser(entity.getUser());

        if (entity.getReceptions() != null) {
            dto.setReceptions(
                    entity.getReceptions().stream()
                            .map(reception -> {
                                var rDto = new com.health_donate.health.dto.ReceptionDTO();
                                rDto.setId(reception.getId());
                                rDto.setEstLu(reception.getEstLu());
                                return rDto;
                            })
                            .collect(Collectors.toList())
            );
        }

        return dto;
    }

    public static Membre toEntity(MembreDTO dto) {
        if (dto == null) return null;

        Membre entity = new Membre();
        entity.setId(dto.getId());
        entity.setActif(dto.getActif());
        entity.setAssociation(AssociationMapper.toEntity(dto.getAssociations()));
        entity.setUser(dto.getUser());
        return entity;
    }
}
