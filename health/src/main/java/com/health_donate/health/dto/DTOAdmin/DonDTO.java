package com.health_donate.health.dto.DTOAdmin;

import com.health_donate.health.dto.DonationRequestDTO;
import com.health_donate.health.enumT.DonationCategory;
import com.health_donate.health.enumT.DonationStatus;
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
public class DonDTO {

    private Long id;
    private String title;

    private String descriptionCourte;
    private String description;

    private DonationCategory category;
    private int quantity;
    private DonationStatus isAvailable;
    private String location;
    private boolean urgent;
    private boolean published;
    private String etat;
    private String typeDon;
    private String raisonDeclin;
    private LocalDateTime createdAt;

    private Long donorId;
    private String donorName;

    private List<String> imageUrls;
    private List<Long> requestIds;
    private List<Long> reviewIds;
    private List<DonationRequestDTO> demandeurs;
}
