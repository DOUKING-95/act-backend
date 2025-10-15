package com.health_donate.health.repository;

import com.health_donate.health.entity.DonationRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DonationRequestRepository extends JpaRepository<DonationRequest, Long> {

    List<DonationRequest> findByDonationId(Long donationId);
}
