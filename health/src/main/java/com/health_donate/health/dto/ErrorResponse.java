package com.health_donate.health.dto;

import java.time.LocalDateTime;

public record ErrorResponse(
        LocalDateTime timestamp,
        String code,
        String error,
        String message,
        String path) {
}
