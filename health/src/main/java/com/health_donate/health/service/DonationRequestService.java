package com.health_donate.health.service;

import com.health_donate.health.dto.DonationRequestDTO;
import com.health_donate.health.entity.DonationRequest;
import com.health_donate.health.mapper.DonationRequestMapper;
import com.health_donate.health.repository.DonationRequestRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class DonationRequestService {


    private DonationRequestRepository donationRequestRepository;

    // CREATE
    public DonationRequestDTO createDonationRequest(DonationRequestDTO dto) {
        DonationRequest request = DonationRequestMapper.toEntity(dto);
        DonationRequest saved = donationRequestRepository.save(request);
        return DonationRequestMapper.toDTO(saved);
    }

    // READ
    public DonationRequestDTO getDonationRequestById(Long id) {
        Optional<DonationRequest> opt = donationRequestRepository.findById(id);
        return opt.map(DonationRequestMapper::toDTO).orElse(null);
    }

    // UPDATE
    public DonationRequestDTO updateDonationRequest(Long id, DonationRequestDTO dto) {
        Optional<DonationRequest> opt = donationRequestRepository.findById(id);
        if (opt.isEmpty()) return null;

        DonationRequest request = opt.get();
        request.setMessage(dto.getMessage());
        request.setStatus(dto.getStatus());

        DonationRequest updated = donationRequestRepository.save(request);
        return DonationRequestMapper.toDTO(updated);
    }

    // DELETE
    public boolean deleteDonationRequest(Long id) {
        if (!donationRequestRepository.existsById(id)) return false;
        donationRequestRepository.deleteById(id);
        return true;
    }
}

