package com.health_donate.health.mapper;

import com.health_donate.health.dto.NotificationDTO;
import com.health_donate.health.entity.Notification;
import com.health_donate.health.entity.Reception;
import com.health_donate.health.repository.ReceptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;


@Component
public class NotificationMapper {

    @Autowired
    private ReceptionRepository receptionRepository;

    public NotificationDTO toDTO(Notification entity) {
        if (entity == null) return null;

        NotificationDTO dto = new NotificationDTO();
        dto.setId(entity.getId());
        dto.setTitre(entity.getTitre());
        dto.setType(entity.getType());
        dto.setContenu(entity.getContenu());
        dto.setDestinataires(entity.getDestinataires());
        dto.setDateCreation(entity.getDateCreation());

        List<Reception> receptions = receptionRepository.findByNotificationId(entity.getId());
        dto.setReceptions(
                receptions.stream()
                        .map(ReceptionMapper::toDTO)
                        .collect(Collectors.toList())
        );

        return dto;
    }

    public Notification toEntity(NotificationDTO dto) {
        if (dto == null) return null;

        Notification entity = new Notification();
        entity.setId(dto.getId());
        entity.setTitre(dto.getTitre());
        entity.setType(dto.getType());
        entity.setContenu(dto.getContenu());
        entity.setDestinataires(dto.getDestinataires());
        entity.setDateCreation(dto.getDateCreation());
        return entity;
    }
}

