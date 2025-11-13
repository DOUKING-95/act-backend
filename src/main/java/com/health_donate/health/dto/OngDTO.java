package com.health_donate.health.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.time.LocalDate;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class OngDTO {
    private Long id;
    private String nom;
    private String typeOrganisation;
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
}