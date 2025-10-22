package com.health_donate.health.dto;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.health_donate.health.entity.Membre;
import com.health_donate.health.entity.Notification;
import com.health_donate.health.entity.Ong;
import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
