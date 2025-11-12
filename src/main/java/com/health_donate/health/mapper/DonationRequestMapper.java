package com.health_donate.health.mapper;



import com.health_donate.health.dto.DonationRequestDTO;
import com.health_donate.health.entity.DonationRequest;
import com.health_donate.health.entity.Donation;
import com.health_donate.health.entity.User;
import com.health_donate.health.repository.DonationRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

public class DonationRequestMapper {

    public static DonationRequestDTO toDTO(DonationRequest request) {
        if (request == null) return null;

        DonationRequestDTO dto = new DonationRequestDTO();
        dto.setId(request.getId());
        dto.setMessage(request.getMessage());
        dto.setStatus(request.getStatus());
        dto.setCreatedAt(request.getCreatedAt());

        if (request.getDonation() != null) {
            dto.setDonationId(request.getDonation().getId());
        }

        if (request.getRequester() != null) {
            dto.setRequesterId(request.getRequester().getId());
            dto.setName(request.getRequester().getName());
            dto.setType(request.getRequester().getAuthorities().toString());
        }

        return dto;
    }

    public static DonationRequest toEntity(DonationRequestDTO dto) {
        if (dto == null) return null;

        DonationRequest request = new DonationRequest();
        request.setId(dto.getId());
        request.setMessage(dto.getMessage());
        request.setStatus(dto.getStatus());
        request.setCreatedAt(dto.getCreatedAt());

        if (dto.getDonationId() != null) {

            Donation donation = new Donation();
            donation.setId(dto.getDonationId());
            request.setDonation(donation);
        }

        if (dto.getRequesterId() != null) {
            User requester = new User();
            requester.setId(dto.getRequesterId());
            request.setRequester(requester);
        }

        return request;
    }
}

