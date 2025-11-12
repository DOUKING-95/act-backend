package com.health_donate.health.service;


import com.health_donate.health.dto.NotificationDTO;
import com.health_donate.health.entity.*;
import com.health_donate.health.mapper.NotificationMapper;
import com.health_donate.health.repository.*;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;
    @Autowired
    private OngRepository ongRepository;
    @Autowired
    private ReceptionRepository receptionRepository;
    @Autowired
    private ActorRepository actorRepository;
    @Autowired
    private MembreRepository membreRepository;
    @Autowired
    private AssociationRepository associationRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private NotificationMapper notificationMapper;

    private SimpMessagingTemplate messagingTemplate;


    // GET BY ID
    public NotificationDTO getNotificationById(Long id) {
        Notification notif = notificationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Notification introuvable"));
        return notificationMapper.toDTO(notif);
    }

    // GET ALL
    public List<NotificationDTO> getAllNotifications() {
        return notificationRepository.findAll().stream()
                .map(notification -> {
                    NotificationDTO dto = notificationMapper.toDTO(notification);

                    // Calcul des stats
                    List<Reception> receptions = receptionRepository.findByNotificationId(notification.getId());
                    long total = receptions.size();
                    long lus = receptions.stream().filter(Reception::getEstLu).count();

                    dto.setEtat(lus + "/" + total);
                    return dto;
                })
                .collect(Collectors.toList());
    }

    @Transactional
    public Notification createNotification(NotificationDTO dto) {
        Notification notif = notificationMapper.toEntity(dto);
        notificationRepository.save(notif);

        Map<String, String> notifData = new HashMap<>();
        notifData.put("title", dto.getTitre());
        notifData.put("description", dto.getContenu());

//        Notification saved = notificationRepository.save(notif);

        switch (dto.getDestinataires().toString().toLowerCase()) {
            case "ong" -> {
                List<Ong> ongList = ongRepository.findAll();
                if (ongList.isEmpty()) {
                    break;
                }
                ongList.forEach(ong -> {
                    messagingTemplate.convertAndSend("/topic/notifications", notifData);
                    Reception reception = new Reception();
                    reception.setNotification(notif);
                    reception.setEstLu(false);
                    reception.setOng(ong);
                    receptionRepository.save(reception);
                });
            }

            case "benevoles" -> {
                List<User> users = userRepository.findAll();
                List<Membre> membres = membreRepository.findAll();
                if (users.isEmpty() || membres.isEmpty()) {
                    break;
                }
                membres.forEach(membre -> {
                    if (users.contains(membre.getUser())) {
                        messagingTemplate.convertAndSend("/topic/notifications", notifData);
                        Reception reception = new Reception();
                        reception.setNotification(notif);
                        reception.setEstLu(false);
                        reception.setMembre(membre);
                        receptionRepository.save(reception);
                    }
                });
            }

            case "associations" -> {
                List<Membre> membreList = membreRepository.findAll();
                List<Association> associations = associationRepository.findAll();
                if (membreList.isEmpty()) {
                    break;
                }
                membreList.forEach(membre -> {
                    if (associations.contains(membre.getAssociation())) {
                        messagingTemplate.convertAndSend("/topic/notifications", notifData);
                        Reception reception = new Reception();
                        reception.setNotification(notif);
                        reception.setEstLu(false);
                        reception.setMembre(membre);
                        receptionRepository.save(reception);
                    }
                });
            }

            case "tous" -> {
//                List<Ong> allOngs = ongRepository.findAll();
                List<User> allMembres = userRepository.findAll();

//                // ONG
//                if (allOngs.isEmpty()) {
//                    break;
//                }
//                allOngs.forEach(ong -> {
//                    Reception reception = new Reception();
//                    reception.setNotification(notif);
//                    reception.setEstLu(false);
//                    reception.setOng(ong);
//                    receptionRepository.save(reception);
//                    log.info("+++++++++++++++++++++++++++++++++++++++");
//                });

                allMembres.forEach(membre -> {
                    messagingTemplate.convertAndSend("/topic/notifications", notifData);
                    Reception reception = new Reception();
                    reception.setNotification(notif);
                    reception.setEstLu(false);
                    reception.setMembre(null);
                    reception.setUser(membre);
                    receptionRepository.save(reception);
                });
            }

            default -> throw new IllegalArgumentException("Type de destinataire inconnu : " + dto.getDestinataires());
        }

        return notif;
    }

    // UPDATE
    public NotificationDTO updateNotification(Long id, NotificationDTO dto) {
        Notification existing = notificationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Notification introuvable"));

        existing.setTitre(dto.getTitre());
        existing.setContenu(dto.getContenu());
        existing.setType(dto.getType());
        existing.setDestinataires(dto.getDestinataires());
        existing.setDateCreation(dto.getDateCreation());

        Notification updated = notificationRepository.save(existing);
        return notificationMapper.toDTO(updated);
    }

    // DELETE
    @Transactional
    public void deleteNotification(Long id) {
        if (!notificationRepository.existsById(id)) {
            throw new RuntimeException("Notification introuvable");
        }
        receptionRepository.deleteAllByNotificationId(id);
        notificationRepository.deleteById(id);
    }

    @Transactional
    public void markAsRead(Long id) {
        Reception reception = receptionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Réception introuvable"));
        reception.setEstLu(true);
        receptionRepository.save(reception);
    }

}

