package com.health_donate.health.mapper;



import com.health_donate.health.dto.ImageDTO;
import com.health_donate.health.entity.Image;
import com.health_donate.health.entity.Donation;

public class ImageMapper {

    // Convertir une entité en DTO
    public static ImageDTO toDTO(Image image) {
        if (image == null) {
            return null;
        }
        ImageDTO dto = new ImageDTO();
        dto.setId(image.getId());
        dto.setUrl(image.getUrl());
        dto.setUploadAt(image.getUploadAt());
        dto.setDonationId(image.getDonation() != null ? image.getDonation().getId() : null);
        return dto;
    }

    // Convertir un DTO en entité
    public static Image toEntity(ImageDTO dto) {
        if (dto == null) {
            return null;
        }
        Image image = new Image();
        image.setId(dto.getId());
        image.setUrl(dto.getUrl());
        image.setUploadAt(dto.getUploadAt());

        if (dto.getDonationId() != null) {
            Donation donation = new Donation();
            donation.setId(dto.getDonationId());
            image.setDonation(donation);
        }

        return image;
    }
}

