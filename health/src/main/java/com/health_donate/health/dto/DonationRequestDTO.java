package com.health_donate.health.dto;


import com.health_donate.health.enumT.RequestStatus;

import java.time.LocalDateTime;

public class DonationRequestDTO {

    private Long id;
    private String message;
    private RequestStatus status;
    private LocalDateTime createdAt;
    private Long donationId;
    private Long requesterId;
}
