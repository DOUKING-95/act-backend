package com.health_donate.health.entity;

import com.health_donate.health.enumT.UserRole;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String email;
    private String password;
    private String phoneNumber;
    private String address;

    @Enumerated(EnumType.STRING)
    private UserRole role;

    private boolean verified;

    private LocalDateTime createdAt = LocalDateTime.now();

    // Relations
    @OneToMany(mappedBy = "donor", cascade = CascadeType.ALL)
    private List<Donation> donations;

    @OneToMany(mappedBy = "requester", cascade = CascadeType.ALL)
    private List<DonationRequest> donationRequests;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Notification> notifications;

    @OneToMany(mappedBy = "sender", cascade = CascadeType.ALL)
    private List<Message> sentMessages;

    @OneToMany(mappedBy = "receiver", cascade = CascadeType.ALL)
    private List<Message> receivedMessages;


}

