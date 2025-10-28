package com.health_donate.health.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class ActorDTO {
    private Long id;
    private String name;
    private String firstname;
    private String email;
    private boolean actif;
    private String phoneNumber;
    private boolean verified;
    private String address;
    private LocalDateTime createdAt;
    private Long roleId;
    private List<Long> donationIds;
    private List<Long> donationRequestIds;
    private List<Long> participationIds;
}
