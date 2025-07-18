package com.health_donate.health.repository;


import com.health_donate.health.entity.Prescription;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PrescriptionRepository extends JpaRepository<Prescription, Long> {
    List<Prescription> findByPharmacieId(Long pharmacieId);
}

