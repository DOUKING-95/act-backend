package com.health_donate.health.entity;

import com.health_donate.health.enumT.StatutAsso;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "association")
public class Association {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String address;
    private String phone;
    private String email;
    private String logoUrl;
    private String covertUrl;
    private boolean isActive;
    private String description;
    private String categorie;

    @Enumerated(EnumType.STRING)
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

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;


    @OneToMany(mappedBy = "association", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SocialAction> socialActions;
}
