package com.health_donate.health.dto;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TopParticipantDTO {
    private Long actorId;
    private Long totalParticipations;
}

