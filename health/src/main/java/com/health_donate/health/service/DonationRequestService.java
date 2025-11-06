package com.health_donate.health.service;

import com.health_donate.health.dto.DonationRequestDTO;
import com.health_donate.health.entity.Donation;
import com.health_donate.health.entity.DonationRequest;
import com.health_donate.health.enumT.RequestStatus;
import com.health_donate.health.mapper.DonationMapper;
import com.health_donate.health.mapper.DonationRequestMapper;
import com.health_donate.health.repository.DonationRequestRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class DonationRequestService {


    private DonationRequestRepository donationRequestRepository;



    public List<DonationRequestDTO> getRequestsByRequesterAndStatus(Long requesterId) {
        return donationRequestRepository.findByRequester_IdAndStatus(requesterId, RequestStatus.APPROVED).stream()
                .map(DonationRequestMapper::toDTO)
                .toList();
    }

    public Page<DonationRequestDTO> getRequestsByRequesterAndStatus(Long requesterId , int page) {

        int size = 5;

        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "id"));

        Page<DonationRequest> donationRequests = donationRequestRepository.findByRequester_IdAndStatus(requesterId, RequestStatus.APPROVED, pageRequest);
        return donationRequests.map(DonationRequestMapper::toDTO);

    }

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
//GET ALL
    public List<DonationRequestDTO> getAllDonationRequests() {
        return donationRequestRepository.findAll().stream()
                .map(DonationRequestMapper::toDTO)
                .toList();
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

