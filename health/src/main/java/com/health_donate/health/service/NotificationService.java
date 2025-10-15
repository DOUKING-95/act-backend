package com.health_donate.health.service;

package com.health_donate.health.service;

import com.health_donate.health.dto.NotificationDTO;
import com.health_donate.health.entity.Notification;
import com.health_donate.health.mapper.NotificationMapper;
import com.health_donate.health.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;

    // CREATE
    public NotificationDTO createNotification(NotificationDTO dto) {
        Notification notif = NotificationMapper.toEntity(dto);
        Notification saved = notificationRepository.save(notif);
        return NotificationMapper.toDTO(saved);
    }

    // READ
    public NotificationDTO getNotificationById(Long id) {
        Optional<Notification> opt = notificationRepository.findById(id);
        return opt.map(NotificationMapper::toDTO).orElse(null);
    }

    // UPDATE
    public NotificationDTO updateNotification(Long id, NotificationDTO dto) {
        Optional<Notification> opt = notificationRepository.findById(id);
        if (opt.isEmpty()) return null;

        Notification notif = opt.get();
        notif.setContent(dto.getContent());
        notif.setRead(dto.isRead());

        Notification updated = notificationRepository.save(notif);
        return NotificationMapper.toDTO(updated);
    }

    // DELETE
    public boolean deleteNotification(Long id) {
        if (!notificationRepository.existsById(id)) return false;
        notificationRepository.deleteById(id);
        return true;
    }
}

