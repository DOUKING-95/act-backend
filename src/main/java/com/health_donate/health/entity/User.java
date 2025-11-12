package com.health_donate.health.entity;

import com.health_donate.health.enumT.UserRole;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;


/**
 * Entite pour de base pour mes utilisateur
 * */
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "users")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String address;

    @Column(nullable = true, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    private boolean isActif = false;

    @Column(nullable = false, unique = true)
    private String phoneNumber;

    private boolean verified;
    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "role_id")
    private Role role;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Membre> membres;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Reception> receptions;

    @PrePersist
    protected void onCreate(){
        this.createdAt = LocalDateTime.now();

    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority( role.getName().toString()));
    }

    @Override
    public String getPassword() { return password; }

    @Override
    public String getUsername() {
        return phoneNumber;
    }

    @Override
    public boolean isAccountNonExpired() {
        return isActif;    }

    @Override
    public boolean isAccountNonLocked() {
        return isActif;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return  isActif;
    }

    @Override
    public boolean isEnabled() {
        return  isActif;
    }
}

