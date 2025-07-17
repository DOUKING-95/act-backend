package com.health_donate.health.entity;



import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Table(name = "roles")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name; // Exemple : ROLE_USER, ROLE_ADMIN

    @ManyToMany(mappedBy = "roles")
    private Set<User> users;
}

