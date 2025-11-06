package com.health_donate.health.repository;

import com.health_donate.health.entity.Donation;
import com.health_donate.health.enumT.DonationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
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

    long countByDonorIdAndIsAvailable(Long donorId, DonationStatus isAvailable);

    @Query("SELECT d.donor.id AS donorId, COUNT(d) AS totalDonations " +
            "FROM Donation d " +
            "GROUP BY d.donor.id " +
            "ORDER BY COUNT(d) DESC")
    List<Object[]> findTop15Donors();



}
