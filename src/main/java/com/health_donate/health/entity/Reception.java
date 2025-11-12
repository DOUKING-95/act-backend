package com.health_donate.health.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "reception")
public class Reception {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Boolean estLu;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "membre_id",nullable = true)
    private Membre membre;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ong_id",nullable = true)
    private Ong ong;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "notification_id",nullable = true)
    private Notification notification;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id",nullable = true)
    private User user;

}
