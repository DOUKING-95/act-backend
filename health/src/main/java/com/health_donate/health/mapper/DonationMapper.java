package com.health_donate.health.mapper;



import com.health_donate.health.dto.DonationDTO;
import com.health_donate.health.entity.*;
import java.util.stream.Collectors;

public class DonationMapper {

    public static DonationDTO toDTO(Donation donation) {
        if (donation == null) return null;

        DonationDTO dto = new DonationDTO();
        dto.setId(donation.getId());
        dto.setTitle(donation.getTitle());
        dto.setDescription(donation.getDescription());
        dto.setCategory(donation.getCategory());
        dto.setQuantity(donation.getQuantity());

        dto.setIsAvailable(donation.getIsAvailable());
        dto.setLocation(donation.getLocation());
        dto.setUrgent(donation.isUrgent());
        dto.setPublished(donation.isPublished());
        dto.setCreatedAt(donation.getCreatedAt());

        dto.setDonorId(donation.getDonor() != null ? donation.getDonor().getId() : null);
        dto.setCategory(donation.getCategory() != null ? donation.getCategory() : null);

        if (donation.getRequests() != null) {
            dto.setRequestIds(
                    donation.getRequests().stream()
                            .map(DonationRequest::getId)
                            .collect(Collectors.toList())
            );
        }

        if (donation.getImages() != null) {
            dto.setImageIds(
                    donation.getImages().stream()
                            .map(Image::getId)
                            .collect(Collectors.toList())
            );
        }

        if (donation.getReviews() != null) {
            dto.setReviewIds(
                    donation.getReviews().stream()
                            .map(Review::getId)
                            .collect(Collectors.toList())
            );
        }

        return dto;
    }

    public static Donation toEntity(DonationDTO dto, Actor actor) {
        if (dto == null) return null;

        Donation donation = new Donation();
        donation.setId(dto.getId());
        donation.setTitle(dto.getTitle());
        donation.setDescription(dto.getDescription());
        donation.setCategory(dto.getCategory());
        donation.setQuantity(dto.getQuantity());

        donation.setIsAvailable(dto.getIsAvailable());
        donation.setLocation(dto.getLocation());
        donation.setUrgent(dto.isUrgent());
        donation.setPublished(dto.isPublished());
        donation.setCreatedAt(dto.getCreatedAt());
        donation.setDonor(actor);


        return donation;
    }
}


