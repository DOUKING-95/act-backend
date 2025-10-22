package com.health_donate.health.entity;

import com.health_donate.health.enumT.StatutDemande;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "demande_adhesion")
public class DemandeAdhesion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private Actor user;

    @ManyToOne
    @JoinColumn(name = "association_id", nullable = false)
    private Association association;

    @Enumerated(EnumType.STRING)
    private StatutDemande statut = StatutDemande.EN_ATTENTE;

    private String message; // message de motivation optionnel

    private LocalDateTime dateDemande = LocalDateTime.now();
}
