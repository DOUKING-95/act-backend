package com.health_donate.health.repository;

import com.health_donate.health.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {
    List<Image> findByDonationId(Long donationId);

    List<Image> findBySocialActionId(Long id);
}
