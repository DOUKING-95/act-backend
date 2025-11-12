package com.health_donate.health.dto.dashboard;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DashboardDTO {

    private long totalAssociations;
    private long totalOngs;
    private long totalDonations;
    private long totalSocialActions;

    // Nombre d'actions par mois
    private Map<String, Long> actionsPerMonth;

    // Nombre d'actions par type
    private Map<String, Long> actionsPerType;

    // Dernières actions sociales
    private List<SocialActionDashboardDTO> latestSocialActions;

    private List<PendingApprovalDTO> pendingApproval;

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class SocialActionDashboardDTO {
        private Long id;
        private String titre;
        private String lieu;
        private String description;
        private boolean passed;
        private String date; // formaté pour JSON
        private String type;
        private int benevolNumber;
        private String associationName; // valeur par défaut si null
    }



    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class PendingApprovalDTO {
        private Long id;
        private String nom;
        private String date;
    }
}
