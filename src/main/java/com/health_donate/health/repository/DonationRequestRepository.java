package com.health_donate.health.repository;

import com.health_donate.health.entity.DonationRequest;
import com.health_donate.health.enumT.RequestStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DonationRequestRepository extends JpaRepository<DonationRequest, Long> {

    boolean existsByRequesterIdAndDonationId(Long requesterId, Long donationId);

    List<DonationRequest> findByDonationId(Long donationId);
    long countByRequester_IdAndStatus(Long requesterId, RequestStatus status);
    List<DonationRequest> findByRequester_IdAndStatus(Long requesterId, RequestStatus status);
    Page<DonationRequest> findByRequester_IdAndStatus(Long requesterId, RequestStatus status, Pageable pageable);



}
