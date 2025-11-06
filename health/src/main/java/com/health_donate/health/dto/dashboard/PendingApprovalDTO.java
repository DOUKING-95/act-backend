package com.health_donate.health.dto.dashboard;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PendingApprovalDTO {
    private String nom;
    private String type;
}
