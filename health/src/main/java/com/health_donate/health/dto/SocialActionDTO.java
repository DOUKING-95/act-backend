package com.health_donate.health.dto;

import java.util.List;

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
