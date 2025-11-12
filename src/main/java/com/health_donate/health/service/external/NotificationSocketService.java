package com.health_donate.health.service.external;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class NotificationSocketService {

    private final SimpMessagingTemplate messagingTemplate;

    public NotificationSocketService(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    // Appelable depuis ton code backend
    public void sendNotification(String message) {
        messagingTemplate.convertAndSend("/topic/notifications", message);
    }
}

