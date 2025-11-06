package com.health_donate.health.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "ong")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Ong {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Informations principales
    private String nom;
    private String typeOrganisation;
    @Column(columnDefinition = "TEXT")
    private String descriptionMission;

    private String profilUrl;
    private String coverUrl;

    // Coordonnées
    private String emailContact;
    private String telephoneContact;
    private String siteWeb;
    private String adresse;
    private String ville;
    private String codePostal;
    private String pays;

    // Représentant légal
    private String nomCompletRepresentant;
    private String fonctionRepresentant;
    private String emailRepresentant;
    private String telephoneRepresentant;

    // Informations administratives
    private String numeroEnregistrement;
    private Date dateCreation;
    private String statut;
    private boolean confirmationOfficielle;
    private boolean estActif;

    @OneToMany(mappedBy = "ong",fetch = FetchType.LAZY,cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Reception> receptions;
}
