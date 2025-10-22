package com.health_donate.health.controller;


import com.health_donate.health.dto.NotificationDTO;
import com.health_donate.health.mapper.NotificationMapper;
import com.health_donate.health.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("auth/notification/")
//@CrossOrigin(origins = "http://localhost:4200")
@RequiredArgsConstructor
public class NotificationController {

    @Autowired
    private NotificationService notificationService;
    @Autowired
    private NotificationMapper notificationMapper;


    //GET BY ID
    @GetMapping("{id}")
    public ResponseEntity<NotificationDTO> getNotificationById(@PathVariable Long id) {
        NotificationDTO dto = notificationService.getNotificationById(id);
        return ResponseEntity.ok(dto);
    }

    // GET ALL
    @GetMapping
    public ResponseEntity<List<NotificationDTO>> getAllNotifications() {
        List<NotificationDTO> notifications = notificationService.getAllNotifications();
        return ResponseEntity.ok(notifications);
    }
    //CREATE
    @PostMapping
    public ResponseEntity<NotificationDTO> createNotification(@RequestBody NotificationDTO dto) {
        NotificationDTO created = notificationMapper.toDTO(notificationService.createNotification(dto));
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    //UPDATE
    @PutMapping("{id}")
    public ResponseEntity<NotificationDTO> updateNotification(
            @PathVariable Long id,
            @RequestBody NotificationDTO dto
    ) {
        NotificationDTO updated = notificationService.updateNotification(id, dto);
        return ResponseEntity.ok(updated);
    }

    //Marquer comme lue (option bonus)
    @PutMapping("{id}/read")
    public ResponseEntity<String> markNotificationAsRead(@PathVariable Long id) {
        notificationService.markAsRead(id);
        return ResponseEntity.ok("Notification marquée comme lue");
    }

    //DELETE
    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteNotification(@PathVariable Long id) {
        notificationService.deleteNotification(id);
        return ResponseEntity.noContent().build();
    }
}
