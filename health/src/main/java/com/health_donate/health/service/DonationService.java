package com.health_donate.health.service;

import com.health_donate.health.entity.Donation;
import com.health_donate.health.repository.DonationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DonationService {
    @Autowired
    private DonationRepository donationRepository;

    public Donation save(Donation donation) {
        return donationRepository.save(donation);
    }

    public List<Donation> getUrgentDonations() {
        return donationRepository.findByUrgentTrue();
    }

    public Optional<Donation> getById(Long id) {
        return donationRepository.findById(id);
    }
}
