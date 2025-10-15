package com.health_donate.health.dto;

import java.util.List;

public record ActorDTO(

    Long id,
     String name,
    String email,
   boolean actif,
   String phoneNumber,
     boolean verified,
     String firstname,
     Long roleId,
   List<Long> donationIds,
     List<Long> donationRequestIds,
     List<Long> participationIds) {
}
