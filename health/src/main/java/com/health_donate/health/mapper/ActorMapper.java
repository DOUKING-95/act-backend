package com.health_donate.health.mapper;



import com.health_donate.health.dto.ActorDTO;
import com.health_donate.health.entity.Actor;
import com.health_donate.health.entity.Role;
import com.health_donate.health.entity.Donation;
import com.health_donate.health.entity.DonationRequest;
import com.health_donate.health.entity.Participation;

import java.util.List;
import java.util.stream.Collectors;

public class ActorMapper {

    public static ActorDTO toDTO(Actor actor) {
        if (actor == null) return null;

        ActorDTO dto = new ActorDTO();
        dto.setId(actor.getId());
        dto.setName(actor.getName());
        dto.setEmail(actor.getEmail());
        dto.setActif(actor.isActif());
        dto.setPhoneNumber(actor.getPhoneNumber());
        dto.setVerified(actor.isVerified());
        dto.setFirstname(actor.getFirstname());

        if (actor.getRole() != null) {
            dto.setRoleId(actor.getRole().getId());
        }

        if (actor.getDonations() != null) {
            dto.setDonationIds(actor.getDonations()
                    .stream()
                    .map(Donation::getId)
                    .collect(Collectors.toList()));
        }

        if (actor.getDonationRequests() != null) {
            dto.setDonationRequestIds(actor.getDonationRequests()
                    .stream()
                    .map(DonationRequest::getId)
                    .collect(Collectors.toList()));
        }

        if (actor.getParticipations() != null) {
            dto.setParticipationIds(actor.getParticipations()
                    .stream()
                    .map(Participation::getId)
                    .collect(Collectors.toList()));
        }

        return dto;
    }

    public static Actor toEntity(ActorDTO dto) {
        if (dto == null) return null;

        Actor actor = new Actor();
        actor.setId(dto.getId());
        actor.setName(dto.getName());
        actor.setEmail(dto.getEmail());
        actor.setActif(dto.isActif());
        actor.setPhoneNumber(dto.getPhoneNumber());
        actor.setVerified(dto.isVerified());
        actor.setFirstname(dto.getFirstname());

        if (dto.getRoleId() != null) {
            Role role = new Role();
            role.setId(dto.getRoleId());
            actor.setRole(role);
        }

        // On ne mappe pas les listes entières ici pour éviter les cycles
        // (elles peuvent être gérées ailleurs via les services)
        return actor;
    }
}

