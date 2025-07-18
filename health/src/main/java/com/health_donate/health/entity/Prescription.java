package com.health_donate.health.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "prescriptions")
public class Prescription extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "pharmacy_id")
    private Pharmacy pharmacy;

    private String fileUrl; // URL de l’ordonnance uploadée (PDF/image)

    private boolean isPaid;

    private String status; // en_attente, confirmée, rejetée...

    // Getters et Setters
}

