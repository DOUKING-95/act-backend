package com.health_donate.health.dto;

public record DashboardSummaryDto(
        long totalAssociations,
        long totalOrganisations,
        long totalDons,
        long totalActivites
) {}