package com.health_donate.health.service;

import com.health_donate.health.entity.Pharmacy;
import com.health_donate.health.repository.PharmacyRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PharmacyService {

    private final PharmacyRepository PharmacyRepository;

    public PharmacyService(PharmacyRepository PharmacyRepository) {
        this.PharmacyRepository = PharmacyRepository;
    }

    public Pharmacy create(Pharmacy Pharmacy) {
        return PharmacyRepository.save(Pharmacy);
    }

    public List<Pharmacy> getAll() {
        return PharmacyRepository.findAll();
    }

    public Optional<Pharmacy> getById(Long id) {
        return PharmacyRepository.findById(id);
    }

    public void delete(Long id) {
        PharmacyRepository.deleteById(id);
    }
}

