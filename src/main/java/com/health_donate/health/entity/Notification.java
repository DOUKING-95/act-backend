package com.health_donate.health.entity;

import com.health_donate.health.enumT.Destinataire;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "notifications")
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titre;

    private String type;

    @Column(columnDefinition = "TEXT")
    private String contenu;

    private Destinataire destinataires;

    private LocalDateTime dateCreation = LocalDateTime.now();

    @OneToMany(mappedBy = "notification",fetch = FetchType.LAZY,cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Reception> receptions;
}

