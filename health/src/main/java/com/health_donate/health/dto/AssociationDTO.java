package com.health_donate.health.dto;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.health_donate.health.enumT.StatutAsso;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.File;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class AssociationDTO {

    private Long id;

    private String name;

    private String address;

    private String phone;

    private String email;

    private String logoUrl;

    private String covertUrl;

    private Boolean active;

    private String description;

    private StatutAsso statut;

    private Date dateCreation;

    private String typeAssociation;

    private String siteWeb;

    private String ville;

    private String codePostal;

    private String pays;

    private String nomComplet;

    private String fonction;

    private String numeroEnregistrement;

    private Boolean confirmationOfficielle;
}
