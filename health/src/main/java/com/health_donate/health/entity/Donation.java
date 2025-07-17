package com.health_donate.health.entity;

import com.health_donate.health.enumT.DonationStatus;
import com.health_donate.health.enumT.DonationType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Donation {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;

    @Enumerated(EnumType.STRING)
    private DonationType type;

    private int quantity;
    private LocalDate expiryDate;

    @Enumerated(EnumType.STRING)
    private DonationStatus status;

    private String location;
    private boolean urgent;
    private LocalDateTime createdAt = LocalDateTime.now();

    // Relations
    @ManyToOne
    @JoinColumn(name = "donor_id")
    private User donor;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @OneToMany(mappedBy = "donation", cascade = CascadeType.ALL)
    private List<DonationRequest> requests;

    @OneToMany(mappedBy = "donation", cascade = CascadeType.ALL)
    private List<Image> images;

    @OneToMany(mappedBy = "donation", cascade = CascadeType.ALL)
    private List<Review> reviews;
}

