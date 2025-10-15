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
        dto.setType(donation.getType());
        dto.setQuantity(donation.getQuantity());
        dto.setExpiryDate(donation.getExpiryDate());
        dto.setStatus(donation.getStatus());
        dto.setLocation(donation.getLocation());
        dto.setUrgent(donation.isUrgent());
        dto.setPublished(donation.isPublished());
        dto.setCreatedAt(donation.getCreatedAt());

        dto.setDonorId(donation.getDonor() != null ? donation.getDonor().getId() : null);
        dto.setCategoryId(donation.getCategory() != null ? donation.getCategory().getId() : null);

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

    public static Donation toEntity(DonationDTO dto, User donor, Category category) {
        if (dto == null) return null;

        Donation donation = new Donation();
        donation.setId(dto.getId());
        donation.setTitle(dto.getTitle());
        donation.setDescription(dto.getDescription());
        donation.setType(dto.getType());
        donation.setQuantity(dto.getQuantity());
        donation.setExpiryDate(dto.getExpiryDate());
        donation.setStatus(dto.getStatus());
        donation.setLocation(dto.getLocation());
        donation.setUrgent(dto.isUrgent());
        donation.setPublished(dto.isPublished());
        donation.setCreatedAt(dto.getCreatedAt());
        donation.setDonor(donor);
        donation.setCategory(category);

        return donation;
    }
}


