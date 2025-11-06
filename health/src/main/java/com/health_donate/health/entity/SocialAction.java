package com.health_donate.health.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "action_social")
public class SocialAction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titre;
    private String lieu;
    private String description;
    private boolean passed;
    private int benevolNumber;
    private LocalDate date;
    private String type;

    @OneToMany(mappedBy = "socialAction", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Image> images = new ArrayList<>();
    public void updateImages(List<Image> newImages) {
        this.images.clear();
        if (newImages != null) {
            this.images.addAll(newImages);
        }
    }

    @OneToMany(mappedBy = "activite", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Participation> participations = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "association_id")
    private Association association;
}
