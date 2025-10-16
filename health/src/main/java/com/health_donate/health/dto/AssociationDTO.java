package com.health_donate.health.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AssociationDTO {

    private Long id;
    private String name;
    private String address;
    private String phone;
    private String email;
    private String logoUrl;
    private String covertUrl;
    private boolean active;
    private String description;
}
