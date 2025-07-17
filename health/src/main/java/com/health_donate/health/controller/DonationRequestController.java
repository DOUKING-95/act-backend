package com.health_donate.health.controller;

import com.health_donate.health.entity.DonationRequest;
import com.health_donate.health.service.DonationRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/requests")
public class DonationRequestController {
    @Autowired
    private DonationRequestService requestService;

    @PostMapping
    public ResponseEntity<DonationRequest> createRequest(@RequestBody DonationRequest request) {
        return ResponseEntity.ok(requestService.save(request));
    }

    @GetMapping("/donation/{donationId}")
    public List<DonationRequest> getRequestsByDonation(@PathVariable Long donationId) {
        return requestService.getRequestsByDonationId(donationId);
    }
}
