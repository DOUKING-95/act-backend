package com.health_donate.health.service;

import com.health_donate.health.dto.dashboard.*;
import com.health_donate.health.enumT.StatutAsso;
import com.health_donate.health.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.format.DateTimeFormatter;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DashboardService {

    private final AssociationRepository associationRepository;
    private final OngRepository ongRepository;
    private final DonationRepository donationRepository;
    private final SocialActionRepository socialActionRepository;



    public DashboardDTO getDashboardData() {
        DashboardDTO dto = new DashboardDTO();

        // Comptages simples
        dto.setTotalAssociations(associationRepository.count());
        dto.setTotalOngs(ongRepository.count());
        dto.setTotalDonations(donationRepository.count());
        dto.setTotalSocialActions(socialActionRepository.count());

        // Actions par mois
        Map<String, Long> actionsPerMonth = socialActionRepository.findActionsPerMonth()
                .stream()
                .filter(entry -> entry.getMonth() != null) // filtre les null
                .collect(Collectors.toMap(
                        SocialActionRepository.MonthCount::getMonth,
                        SocialActionRepository.MonthCount::getCount
                ));
        dto.setActionsPerMonth(actionsPerMonth);

        // Actions par type
        Map<String, Long> actionsPerType = socialActionRepository.findActionsPerType()
                .stream()
                .filter(entry -> entry.getType() != null)
                .collect(Collectors.toMap(
                        SocialActionRepository.TypeCount::getType,
                        SocialActionRepository.TypeCount::getCount
                ));
        dto.setActionsPerType(actionsPerType);

        // Dernières actions sociales
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        List<DashboardDTO.SocialActionDashboardDTO> latestActions =
                socialActionRepository.findLatestActions()
                        .stream()
                        .map(sa -> new DashboardDTO.SocialActionDashboardDTO(
                                sa.getId(),
                                sa.getTitre(),
                                sa.getLieu(),
                                sa.getDescription(),
                                sa.isPassed(),
                                sa.getDate() != null ? sa.getDate().format(formatter) : "N/A",
                                sa.getType(),
                                sa.getBenevolNumber(),
                                sa.getAssociation() != null ? sa.getAssociation().getName() : "N/A"
                        ))
                        .collect(Collectors.toList());

        dto.setLatestSocialActions(latestActions);

        List<DashboardDTO.PendingApprovalDTO> pendinApproval  = associationRepository.findTop3ByStatut(StatutAsso.En_attente)
                .stream()
                .map(sa -> new DashboardDTO.PendingApprovalDTO(
                        sa.getId(),
                        sa.getName(),
                        sa.getDateCreation()!= null ? sa.getDateCreation().toString() : "N/A"
                ))
                .toList();

        dto.setPendingApproval(pendinApproval);

        return dto;
    }
}
