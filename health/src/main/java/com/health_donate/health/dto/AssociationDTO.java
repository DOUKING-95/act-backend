package com.health_donate.health.dto;


import com.health_donate.health.enumT.StatutAsso;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AssociationDTO {

    private Long id;

    private String name;

    private String address;

    private String phone;

    private String email;

    private String logoUrl;

    private String covertUrl;

    private boolean active;

    private String description;

    private String categorie;

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

    private Long userId;

    private List<Long> benevolesIds;
    private List<Long> socialActionIds;
}
