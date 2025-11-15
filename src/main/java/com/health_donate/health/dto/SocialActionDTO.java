package com.health_donate.health.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SocialActionDTO {

    private Long id;
    private String titre;
    private Long associationId;
    private String lieu;
    private LocalDateTime dateDebut;
    private LocalDateTime dateFin;

    String heureDebut;
    String heureFin;
    String infosSupplementaires;
    private String description;

    private List<Long> imageIds;
    private boolean passed;
    private LocalDate date = LocalDate.now();
    private String type;
    private int benevolNumber;
    private List<Long> participationIds;


}
