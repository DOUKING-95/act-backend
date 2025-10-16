package com.health_donate.health.mapper;



import com.health_donate.health.dto.AssociationDTO;
import com.health_donate.health.entity.Association;

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
        association.setActive(dto.isActive());
        association.setDescription(dto.getDescription());

        return association;
    }
}

