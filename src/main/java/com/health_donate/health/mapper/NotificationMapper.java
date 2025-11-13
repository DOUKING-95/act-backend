package com.health_donate.health.mapper;

import com.health_donate.health.dto.MembreDTO;
import com.health_donate.health.dto.NotificationDTO;
import com.health_donate.health.dto.OngDTO;
import com.health_donate.health.dto.ReceptionDTO;
import com.health_donate.health.entity.Notification;
import com.health_donate.health.entity.Reception;
import com.health_donate.health.repository.ReceptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

//public class NotificationMapper {
//
//    private final ReceptionRepository receptionRepository;
//
//    //ENTITY → DTO
//    public NotificationDTO toDTO(Notification notification) {
//        if (notification == null) return null;
//
//        NotificationDTO dto = new NotificationDTO();
//        dto.setId(notification.getId());
//        dto.setTitre(notification.getTitre());
//        dto.setType(notification.getType());
//        dto.setContenu(notification.getContenu());
//        dto.setDestinataires(notification.getDestinataires());
//        dto.setDateCreation(notification.getDateCreation());
//
//        //Récupération des réceptions associées
//        List<Reception> receptions = receptionRepository.findByNotificationId(notification.getId());
//        if (receptions != null && !receptions.isEmpty()) {
//            List<ReceptionDTO> receptionDTOList = receptions.stream()
//                    .map(this::toReceptionDTO)
//                    .collect(Collectors.toList());
//            dto.setReceptions(receptionDTOList);
//        }
//
//        return dto;
//    }
//
//    //DTO → ENTITY
//    public Notification toEntity(NotificationDTO dto) {
//        if (dto == null) return null;
//
//        Notification notification = new Notification();
//        notification.setId(dto.getId());
//        notification.setTitre(dto.getTitre());
//        notification.setType(dto.getType());
//        notification.setContenu(dto.getContenu());
//        notification.setDestinataires(dto.getDestinataires());
//        notification.setDateCreation(dto.getDateCreation());
//        return notification;
//    }
//
//    //Reception → ReceptionDTO
//    private ReceptionDTO toReceptionDTO(Reception reception) {
//        ReceptionDTO dto = new ReceptionDTO();
//        dto.setId(reception.getId());
//        dto.setEstLu(reception.getEstLu());
//
//        if (reception.getMembre() != null) {
//            MembreDTO membreDTO = new MembreDTO();
//            membreDTO.setId(reception.getMembre().getId());
//            membreDTO.setUser(reception.getMembre().getUser());
//            membreDTO.setAssociation(reception.getMembre().getAssociation());
//            dto.setMembre(membreDTO);
//        }
//
//        if (reception.getOng() != null) {
//            OngDTO ongDTO = new OngDTO();
//            ongDTO.setId(reception.getOng().getId());
//            ongDTO.setNom(reception.getOng().getNom());
//            ongDTO.setEmailContact(reception.getOng().getEmailContact());
//            ongDTO.setTelephoneContact(reception.getOng().getTelephoneContact());
//            ongDTO.setLogoUrl(reception.getOng().getLogoUrl());
//            dto.setOng(ongDTO);
//        }
//
//        dto.setNotification(null);
//        return dto;
//    }
//}
//


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

