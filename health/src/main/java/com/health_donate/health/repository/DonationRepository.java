package com.health_donate.health.repository;

import com.health_donate.health.entity.Donation;
import com.health_donate.health.enumT.DonationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Repository
public interface DonationRepository extends JpaRepository<Donation, Long> {
    List<Donation> findByUrgentTrue();
    List<Donation> findByDonorId(Long donorId);


        List<Donation> findTop4ByOrderByIdDesc();

    Page<Donation> findByDonorId(Long donorId, Pageable pageable);
    Page<Donation> findAll(Pageable pageable);

    long countByDonorIdAndStatus(Long donorId, DonationStatus status);


}
