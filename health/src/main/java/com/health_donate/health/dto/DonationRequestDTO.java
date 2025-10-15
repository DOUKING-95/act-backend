package com.health_donate.health.dto;


import com.health_donate.health.enumT.RequestStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DonationRequestDTO {

    private Long id;
    private String message;
    private RequestStatus status;
    private LocalDateTime createdAt;
    private Long donationId;
    private Long requesterId;
}
