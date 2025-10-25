package com.health_donate.health.dto;

public record RecentActivityDto(
        String type,
        String description,
        String tempsDepuis
) {}