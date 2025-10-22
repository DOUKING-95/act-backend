package com.health_donate.health.mapper;

import com.health_donate.health.dto.ReceptionDTO;
import com.health_donate.health.entity.Reception;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReceptionMapper {

    public static ReceptionDTO toDTO(Reception entity) {
        if (entity == null) return null;

        ReceptionDTO dto = new ReceptionDTO();
        dto.setId(entity.getId());
        dto.setEstLu(entity.getEstLu());
        dto.setMembre(MembreMapper.toDTO(entity.getMembre()));
        dto.setOng(OngMapper.toDTO(entity.getOng()));
        dto.setNotification(null); // éviter la récursion
        return dto;
    }

    public static Reception toEntity(ReceptionDTO dto) {
        if (dto == null) return null;

        Reception entity = new Reception();
        entity.setId(dto.getId());
        entity.setEstLu(dto.getEstLu());
        entity.setMembre(MembreMapper.toEntity(dto.getMembre()));
        entity.setOng(OngMapper.toEntity(dto.getOng()));
        return entity;
    }
}
