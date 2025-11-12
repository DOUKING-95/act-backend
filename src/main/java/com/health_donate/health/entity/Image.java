package com.health_donate.health.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;


/**
 * Entite pour pour enregister les url des images  sur DoNup
 * */
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "images")
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String url;
    private String name;

    private  LocalDateTime uploadAt;


    @ManyToOne
    @JoinColumn(name = "donation_id")
    private Donation donation;

    @ManyToOne
    @JoinColumn(name = "social_action_id")
    private SocialAction socialAction;

    @PrePersist
    protected void onCreate() {
        this.uploadAt = LocalDateTime.now();

    }
}

