package com.health_donate.health.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "action_social")
public class SocialAction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titre;
    private String lieu;
    private LocalDateTime dateDebut;
    private LocalDateTime dateFin;
    private  String heureDebut;
    private  String heureFin;
   private  String infosSupplementaires ;
    @Column(name = "is_active", nullable = false)
    private boolean isActive = true;
    private String description;
    private boolean passed;
    private int benevolNumber;

    @OneToMany(mappedBy = "socialAction", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Image> images = new ArrayList<>();

    @OneToMany(mappedBy = "activite", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Participation> participations = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "association_id", nullable = false)
    private Association association;
}
