package com.health_donate.health.entity;

import com.health_donate.health.enumT.DonationCategory;
import com.health_donate.health.enumT.DonationStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;


/**
 * Entite pour pour enregister les dons sur DoNup
 * */
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "donations")
public class Donation {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;

    @Enumerated(EnumType.STRING)
    private DonationCategory category;

    private int quantity;


    @Enumerated(EnumType.STRING)
    private DonationStatus isAvailable;

    private String location;
    private boolean urgent;

    private  boolean published;
    private LocalDateTime createdAt ;


    @ManyToOne
    @JoinColumn(name = "donor_id")
    private Actor donor;




    @OneToMany(mappedBy = "donation", cascade = CascadeType.ALL)
    private List<DonationRequest> requests;

    @OneToMany(mappedBy = "donation", cascade = CascadeType.ALL)
    private List<Image> images;

    @OneToMany(mappedBy = "donation", cascade = CascadeType.ALL)
    private List<Review> reviews;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();

    }
}

