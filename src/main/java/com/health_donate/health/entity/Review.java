package com.health_donate.health.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * Entite pour pour noter le qualite de mes dons
 * */
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "reviews")
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int rating;
    private String comment;
    private LocalDateTime reviewAt;


    @ManyToOne
    @JoinColumn(name = "donation_id")
    private Donation donation;

    @ManyToOne
    @JoinColumn(name = "reviewer_id")
    private User reviewer;

    protected  void onCreate(){
        this.reviewAt = LocalDateTime.now();
    }
}

