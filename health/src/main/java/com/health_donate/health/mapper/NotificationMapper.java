package com.health_donate.health.mapper;



import com.health_donate.health.dto.NotificationDTO;
import com.health_donate.health.entity.Notification;
import com.health_donate.health.entity.User;

public class NotificationMapper {

    public static NotificationDTO toDTO(Notification notification) {
        if (notification == null) return null;

        NotificationDTO dto = new NotificationDTO();
        dto.setId(notification.getId());
        dto.setContent(notification.getContent());
        dto.setRead(notification.isRead());
        dto.setCreatedAt(notification.getCreatedAt());

        if (notification.getUser() != null) {
            dto.setUserId(notification.getUser().getId());
        }

        return dto;
    }

    public static Notification toEntity(NotificationDTO dto) {
        if (dto == null) return null;

        Notification notification = new Notification();
        notification.setId(dto.getId());
        notification.setContent(dto.getContent());
        notification.setRead(dto.isRead());
        notification.setCreatedAt(dto.getCreatedAt());

        if (dto.getUserId() != null) {
            User user = new User();
            user.setId(dto.getUserId());
            notification.setUser(user);
        }

        return notification;
    }
}

