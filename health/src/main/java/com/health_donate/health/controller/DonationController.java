package com.health_donate.health.controller;

import com.health_donate.health.entity.Donation;
import com.health_donate.health.service.DonationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/donations")
public class DonationController {
    @Autowired
    private DonationService donationService;

    @PostMapping
    public ResponseEntity<Donation> createDonation(@RequestBody Donation donation) {
        return ResponseEntity.ok(donationService.save(donation));
    }

    @GetMapping("/urgent")
    public List<Donation> getUrgentDonations() {
        return donationService.getUrgentDonations();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Donation> getDonation(@PathVariable Long id) {
        return donationService.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
