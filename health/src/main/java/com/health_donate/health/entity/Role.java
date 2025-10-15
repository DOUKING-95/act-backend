package com.health_donate.health.entity;



import com.health_donate.health.enumT.UserRole;
import jakarta.persistence.*;
import lombok.*;




/**
 * Entite pour de base pour representer mes roles
 * */
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
    @Enumerated(EnumType.STRING)
    private UserRole name;


}

