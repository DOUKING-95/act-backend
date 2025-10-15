package com.health_donate.health.mapper;


import com.health_donate.health.dto.OngDTO;
import com.health_donate.health.entity.Ong;

public class OngMapper {

    public static OngDTO toDTO(Ong ong) {
        if (ong == null) return null;

        OngDTO dto = new OngDTO();
        dto.setId(ong.getId());
        dto.setName(ong.getName());
        dto.setAddress(ong.getAddress());
        dto.setPhone(ong.getPhone());
        dto.setEmail(ong.getEmail());
        dto.setLogoUrl(ong.getLogoUrl());
        dto.setActive(ong.isActive());
        dto.setDescription(ong.getDescription());

        return dto;
    }

    public static Ong toEntity(OngDTO dto) {
        if (dto == null) return null;

        Ong ong = new Ong();
        ong.setId(dto.getId());
        ong.setName(dto.getName());
        ong.setAddress(dto.getAddress());
        ong.setPhone(dto.getPhone());
        ong.setEmail(dto.getEmail());
        ong.setLogoUrl(dto.getLogoUrl());
        ong.setActive(dto.isActive());
        ong.setDescription(dto.getDescription());

        return ong;
    }
}

