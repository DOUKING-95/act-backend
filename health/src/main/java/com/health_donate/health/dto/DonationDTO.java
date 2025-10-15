package com.health_donate.health.dto;


import com.health_donate.health.enumT.DonationCategory;
import com.health_donate.health.enumT.DonationStatus;
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
public class DonationDTO {

    private Long id;
    private String title;
    private String description;
    private DonationCategory type;
    private int quantity;
    private LocalDate expiryDate;
    private DonationStatus isAvailable;
    private String location;
    private boolean urgent;
    private boolean published;
    private LocalDateTime createdAt;

    private Long donorId;
    private String category;

    private List<Long> requestIds;
    private List<Long> imageIds;
    private List<Long> reviewIds;

}
