package com.health_donate.health.dto;

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
public class DonationDTO {
    private Long id;
    private String title;
    private String description;
    private Double amount;
    private LocalDateTime createdAt;
    private Long userId; // référence à l’utilisateur qui a posté le don
    private List<Long> imageIds;
}
