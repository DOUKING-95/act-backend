package com.health_donate.health.controller;

import com.health_donate.health.dto.ApprovalDto;
import com.health_donate.health.dto.DashboardChartDataDto;
import com.health_donate.health.dto.DashboardSummaryDto;
import com.health_donate.health.dto.RecentActivityDto;
import com.health_donate.health.service.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("auth/dashboard")
public class DashboardController {

    @Autowired
    private DashboardService dashboardService;

    //Endpoint pour les cartes numériques
    @GetMapping("/summary")
    public ResponseEntity<DashboardSummaryDto> getSummary() {
        DashboardSummaryDto summary = dashboardService.getSummaryData();
        return ResponseEntity.ok(summary);
    }

    //Endpoint pour les graphiques (tout en un pour plus d'efficacité)
    @GetMapping("/charts")
    public ResponseEntity<DashboardChartDataDto> getCharts() {
        DashboardChartDataDto charts = dashboardService.getChartData();
        return ResponseEntity.ok(charts);
    }

    //Endpoint pour les activités récentes
    @GetMapping("/activities/recent")
    public ResponseEntity<List<RecentActivityDto>> getRecentActivity() {
        List<RecentActivityDto> activity = dashboardService.getRecentActivity();
        return ResponseEntity.ok(activity);
    }

    //Endpoint pour les approbations en attente
    @GetMapping("/approvals/pending")
    public ResponseEntity<List<ApprovalDto>> getPendingApprovals() {
        List<ApprovalDto> approvals = dashboardService.getPendingApprovals();
        return ResponseEntity.ok(approvals);
    }

    //Endpoint pour traiter l'approbation
    @PostMapping("/approvals/{id}/approve")
    public ResponseEntity<Void> approveEntity(@PathVariable long id) {
        return ResponseEntity.noContent().build();
    }
}
