package com.health_donate.health.dto;



import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DemandeAdhesionRequest {
    private Long userId;
    private Long associationId;
    private String message;
}

