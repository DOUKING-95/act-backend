package com.health_donate.health.repository;

import com.health_donate.health.entity.Donation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DonationRepository extends JpaRepository<Donation, Long> {
    List<Donation> findByUrgentTrue();
    List<Donation> findByDonorId(Long donorId);
}
