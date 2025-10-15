package com.health_donate.health.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SocialActionDTO {

    private Long id;
    private String titre;
    private String lieu;
    private String description;
    private List<Long> imageIds;
    private boolean passed;
    private int benevolNumber;
    private List<Long> participationIds;
}
