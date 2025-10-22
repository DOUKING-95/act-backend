package com.health_donate.health.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.health_donate.health.entity.Association;
import com.health_donate.health.entity.Reception;
import com.health_donate.health.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class MembreDTO {

    private Long id;

    private Boolean actif;

    private AssociationDTO associations;

    private Association association;

    private User user;

    private List<ReceptionDTO> receptions;
}
