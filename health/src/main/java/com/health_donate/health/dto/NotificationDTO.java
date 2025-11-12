package com.health_donate.health.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.health_donate.health.enumT.Destinataire;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class NotificationDTO {

    private Long id;
    private String titre;

    private String type;

    private String contenu;

    private Destinataire destinataires;

    private LocalDateTime dateCreation = LocalDateTime.now();

    private String etat;

    private List<ReceptionDTO> receptions;
}
