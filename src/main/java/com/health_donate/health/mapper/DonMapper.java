package com.health_donate.health.mapper;


import com.health_donate.health.dto.DTOAdmin.DonDTO;
import com.health_donate.health.entity.*;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class DonMapper {

    public static DonDTO toDTO(Donation donation) {
        if (donation == null) return null;

        DonDTO dto = new DonDTO();
        dto.setId(donation.getId());
        dto.setTitle(donation.getTitle());
        dto.setDescriptionCourte(donation.getDescriptionCourte());
        dto.setDescription(donation.getDescription());
        dto.setCategory(donation.getCategory());
        dto.setQuantity(donation.getQuantity());
        dto.setIsAvailable(donation.getIsAvailable());
        dto.setLocation(donation.getLocation());
        dto.setUrgent(donation.isUrgent());
        dto.setPublished(donation.isPublished());
        dto.setEtat(donation.getEtat());
        dto.setTypeDon(donation.getTypeDon());
        dto.setRaisonDeclin(donation.getRaisonDeclin());
        dto.setCreatedAt(donation.getCreatedAt());

        if (donation.getDonor() != null) {
            dto.setDonorId(donation.getDonor().getId());
            dto.setDonorName(donation.getDonor().getName());
        }

        dto.setImageUrls(donation.getImages() != null
                ? donation.getImages().stream().map(Image::getUrl).collect(Collectors.toList())
                : Collections.emptyList());

        dto.setRequestIds(donation.getRequests() != null
                ? donation.getRequests().stream().map(DonationRequest::getId).collect(Collectors.toList())
                : Collections.emptyList());

        dto.setReviewIds(donation.getReviews() != null
                ? donation.getReviews().stream().map(Review::getId).collect(Collectors.toList())
                : Collections.emptyList());
        dto.setDemandeurs(donation.getRequests() != null
                ? donation.getRequests().stream().map(DonationRequestMapper::toDTO).collect(Collectors.toList())
                : Collections.emptyList());

        return dto;
    }

    public static Donation toEntity(DonDTO dto, Actor actor) {
        if (dto == null) return null;

        Donation donation = new Donation();
        donation.setTitle(dto.getTitle());
        donation.setDescriptionCourte(dto.getDescriptionCourte());
        donation.setDescription(dto.getDescription());
        donation.setCategory(dto.getCategory());
        donation.setQuantity(dto.getQuantity());
        donation.setIsAvailable(dto.getIsAvailable());
        donation.setLocation(dto.getLocation());
        donation.setUrgent(dto.isUrgent());
        donation.setPublished(dto.isPublished());
        donation.setEtat(dto.getEtat());
        donation.setTypeDon(dto.getTypeDon());
        donation.setRaisonDeclin(dto.getRaisonDeclin());
        donation.setDonor(actor);
        return donation;
    }
}
