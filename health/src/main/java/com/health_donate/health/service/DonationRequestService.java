package com.health_donate.health.service;

import com.health_donate.health.entity.DonationRequest;
import com.health_donate.health.repository.DonationRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DonationRequestService {
    @Autowired
    private DonationRequestRepository requestRepository;

    public DonationRequest save(DonationRequest request) {
        return requestRepository.save(request);
    }

    public List<DonationRequest> getRequestsByDonationId(Long donationId) {
        return requestRepository.findByDonationId(donationId);
    }
}
