package com.health_donate.health.dto;

import java.time.LocalDateTime;

public class NotificationDTO {

    private Long id;
    private String content;
    private boolean read;
    private LocalDateTime createdAt;
    private Long userId;
}
