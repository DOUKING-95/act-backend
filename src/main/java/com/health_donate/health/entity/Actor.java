package com.health_donate.health.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Actor")
public class Actor  extends  User{

    private String firstname;
    private double longitude;
    private double latitude;

    @OneToMany(mappedBy = "donor", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Donation> donations;

    @OneToMany(mappedBy = "requester", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<DonationRequest> donationRequests = new ArrayList<>();

    @OneToMany(mappedBy = "actor", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Participation> participations;


}
