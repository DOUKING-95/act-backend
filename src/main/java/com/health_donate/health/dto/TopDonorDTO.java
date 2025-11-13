package com.health_donate.health.dto;


import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class TopDonorDTO {
    private Long donorId;
    private Long totalDonations;

    public TopDonorDTO(Long donorId, Long totalDonations) {
        this.donorId = donorId;
        this.totalDonations = totalDonations;
    }


}

