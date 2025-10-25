package com.health_donate.health.service;

import com.health_donate.health.dto.*;
import com.health_donate.health.repository.AssociationRepository;
import com.health_donate.health.repository.DonationRepository;
import com.health_donate.health.repository.OngRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DashboardService {

    @Autowired
    private AssociationRepository associationRepo;
    @Autowired private OngRepository organisationRepo;
    @Autowired private DonationRepository donRepo;
//    @Autowired private ActiviteRepository activiteRepo;

    //Récupération des données globales
    public DashboardSummaryDto getSummaryData() {
        return new DashboardSummaryDto(
                associationRepo.count(),
                organisationRepo.count(),
                donRepo.count(),
                0
        );
    }

    //Récupération des données pour les graphiques
    public DashboardChartDataDto getChartData() {

        return new DashboardChartDataDto(null,null);
    }

    //Récupération des activités récentes
    public List<RecentActivityDto> getRecentActivity() {
        return List.of(
                new RecentActivityDto("Nouvelle association", "Association Santé Pour Tous", "Il y a 23 minutes"),
                new RecentActivityDto("Don enregistré", "10 fauteuils roulants", "Il y a 2 heures")
                // ... autres données du repo
        );
    }

    //Récupération des approbations en attente
    public List<ApprovalDto> getPendingApprovals() {
        return associationRepo.findByStatut("EN_ATTENTE").stream()
                .map(assoc -> new ApprovalDto(assoc.getId(), assoc.getNomComplet(), "Association"))
                .collect(Collectors.toList());

    }
}