package com.health_donate.health.dto;

import java.time.LocalDateTime;

public class AuditLogDTO {
    private Long id;
    private String action;
    private LocalDateTime timestamp;
    private Long userId;

}
