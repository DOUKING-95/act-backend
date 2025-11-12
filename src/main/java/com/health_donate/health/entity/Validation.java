package com.health_donate.health.entity;




import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "validation")
public class Validation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Instant expiration;
    private Instant creation;
    private Instant activation;
    private String code;

    @OneToOne(cascade = CascadeType.ALL)
    private User user;
}

