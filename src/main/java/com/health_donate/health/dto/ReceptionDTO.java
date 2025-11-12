package com.health_donate.health.dto;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ReceptionDTO {

    private Long id;

    private Boolean estLu;

    private MembreDTO membre;

    private OngDTO ong;

    private NotificationDTO notification;
}
