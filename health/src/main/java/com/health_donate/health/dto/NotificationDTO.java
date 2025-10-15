package com.health_donate.health.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NotificationDTO {

    private Long id;
    private String content;
    private boolean read;
    private LocalDateTime createdAt;
    private Long userId;
}
