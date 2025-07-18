package com.health_donate.health.service;

import com.health_donate.health.entity.Prescription;
import com.health_donate.health.repository.PharmacieRepository;
import com.health_donate.health.repository.PharmacyRepository;
import com.health_donate.health.repository.PrescriptionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PrescriptionService {

    private final PrescriptionRepository prescriptionRepository;
    private final PharmacyRepository pharmacieRepository;

    public PrescriptionService(PrescriptionRepository prescriptionRepository, PharmacyRepository pharmacieRepository) {
        this.prescriptionRepository = prescriptionRepository;
        this.pharmacieRepository = pharmacieRepository;
    }

    public Prescription create(Prescription prescription) {
        return prescriptionRepository.save(prescription);
    }

    public List<Prescription> getAll() {
        return prescriptionRepository.findAll();
    }

    public List<Prescription> getByPharmacieId(Long pharmacieId) {
        return prescriptionRepository.findByPharmacieId(pharmacieId);
    }
}

