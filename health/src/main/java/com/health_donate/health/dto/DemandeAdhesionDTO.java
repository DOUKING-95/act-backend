package com.health_donate.health.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DemandeAdhesionDTO {
    private Long id;
    private Long userId;
    private Long associationId;
    private String message;
    private String statut;
    private LocalDateTime dateDemande;

    private ActorDTO user;
    private AssociationDTO association;
}
