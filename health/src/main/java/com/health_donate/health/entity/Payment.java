package com.health_donate.health.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;

import java.time.LocalDateTime;

@Entity
public class Payment extends BaseEntity {

    @OneToOne
    private Prescription prescription;

    private String paymentMethod; // Mobile Money, Carte...

    private String transactionId;

    private LocalDateTime paidAt;

    private Double amount;

    private boolean isConfirmed;
}

