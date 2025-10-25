package com.health_donate.health.dto;

import java.util.List;

public record DashboardChartDataDto(
        List<ChartEntry> activitesParMois,
        List<ChartEntry> typesActivites
) {}
