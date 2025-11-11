package com.health_donate.health.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("notifications")
public class NotificationSocketController {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    //  Envoi d'une notification à tous les abonnés du topic
    @PostMapping("/send")
    public void sendNotification(@RequestBody Map<String ,String> messageData) {
        messagingTemplate.convertAndSend("/topic/notifications", messageData);
    }

    //  (Optionnel) Pour envoi à un utilisateur spécifique
    @PostMapping("/send-to-user/{userId}")
    public void sendToUser(@PathVariable String userId, @RequestBody String message) {
        messagingTemplate.convertAndSendToUser(userId, "/queue/notifications", message);
    }
}


