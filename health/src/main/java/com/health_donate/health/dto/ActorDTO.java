package com.health_donate.health.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ActorDTO {

    private Long id;
    private String name;
    private String email;
    private boolean actif;
    private String phoneNumber;
    private boolean verified;

    public ActorDTO(String name, String email, String phoneNumber, String firstname) {
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.firstname = firstname;
    }

    private String firstname;
    private Long roleId;
    private List<Long> donationIds;
    private List<Long> donationRequestIds;
    private List<Long> participationIds;
}
