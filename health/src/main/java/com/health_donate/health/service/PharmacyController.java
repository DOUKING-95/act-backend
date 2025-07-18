package com.health_donate.health.service;

import com.health_donate.health.dto.PharmacyDto;
import com.health_donate.health.entity.Pharmacy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pharmacies")
public class PharmacyController {

    @Autowired
    private PharmacyService pharmacyService;

    @PostMapping
    public ResponseEntity<Pharmacy> create(@RequestBody Pharmacy dto) {
        return ResponseEntity.ok(pharmacyService.create(dto));
    }

    @GetMapping
    public List<Pharmacy> getAll() {
        return pharmacyService.getAll();
    }
}

