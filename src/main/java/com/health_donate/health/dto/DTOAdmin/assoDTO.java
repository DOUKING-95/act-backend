package com.health_donate.health.dto.DTOAdmin;

import com.health_donate.health.enumT.StatutAsso;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.File;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class assoDTO {
    private Long id;
    private String nom;
    private String categorie;
    private String email;
    private String telephone;
    private StatutAsso statut;
    private Date dateCreation;
    private String adresse;
    private String description;
    private Boolean estActif;
    private String typeAssociation;
    private File logoFile;
    private String siteWeb;
    private String ville;
    private String codePostal;
    private String pays;
    private String nomComplet;
    private String fonction;
    private String numeroEnregistrement;
    private String confirmationOfficielle;
}
