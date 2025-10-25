package com.health_donate.health.service;

import com.health_donate.health.dto.DonationRequestDTO;
import com.health_donate.health.entity.Donation;
import com.health_donate.health.entity.DonationRequest;
import com.health_donate.health.entity.User;
import com.health_donate.health.enumT.DonationStatus;
import com.health_donate.health.enumT.RequestStatus;
import com.health_donate.health.mapper.DonationRequestMapper;
import com.health_donate.health.repository.DonationRepository;
import com.health_donate.health.repository.DonationRequestRepository;
import com.health_donate.health.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class DonationRequestService {


    private DonationRequestRepository donationRequestRepository;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DonationRepository donationRepository;

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
    public DonationRequestDTO updateDonationRequest(Long idRequest,Boolean valid) {
        Optional<DonationRequest> opt = donationRequestRepository.findById(idRequest);
        if (opt.isEmpty()) return null;

        DonationRequest request = opt.get();
        if (valid){
            request.setStatus(RequestStatus.APPROVED);
            Optional<Donation> donation = donationRepository.findById(opt.get().getDonation().getId());
            if (donation.isPresent()) {
                donation.get().setIsAvailable(DonationStatus.LIVRE);
                donationRepository.save(donation.get());
            }
        }else {
            request.setStatus(RequestStatus.REJECTED);
        }

        DonationRequest updated = donationRequestRepository.save(request);
        return DonationRequestMapper.toDTO(updated);
    }

    // DELETE
    public boolean deleteDonationRequest(Long id) {
        if (!donationRequestRepository.existsById(id)) return false;
        donationRequestRepository.deleteById(id);
        return true;
    }

    public DonationRequestDTO createDemandeDon(DonationRequestDTO dto) {
        if (dto.getId() != null) {
            dto.setId(null);
        }

        User user = userRepository.findById(dto.getRequesterId()).orElseThrow();
        Donation donation = donationRepository.findById(dto.getDonationId()).orElseThrow();

        DonationRequest donationRequest = new DonationRequest(
                null,
                dto.getMessage(),
                dto.getStatus(),
                dto.getCreatedAt(),
                donation,
                user
        );
        DonationRequest demandeDon = donationRequestRepository.save(donationRequest);
        return DonationRequestMapper.toDTO(demandeDon);
    }

    public DonationRequestDTO assignerDon(Long id,Long userID) {
        User user = userRepository.findById(userID).orElseThrow();
        Optional<Donation> donation = donationRepository.findById(id);
        Donation don = new Donation();
        if (donation.isPresent()) {
            donation.get().setIsAvailable(DonationStatus.LIVRE);
            don = donationRepository.save(donation.get());
        }
        DonationRequest donationRequest = new DonationRequest(
                null,
                "Directement assigner.",
                RequestStatus.APPROVED,
                LocalDate.now().atStartOfDay(),
                don,
                user
        );
        DonationRequest assigneDon = donationRequestRepository.save(donationRequest);
        return DonationRequestMapper.toDTO(assigneDon);
    }


}

