package com.health_donate.health.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "pharmacies")
public class Pharmacy extends BaseEntity {

    private String name;

    private String address;

    private String phone;

    private String email;

    private String logoUrl;

    private boolean isActive;

    // Getters et Setters
}

