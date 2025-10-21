package com.health_donate.health.controller;

import com.health_donate.health.dto.ApiResponse;
import com.health_donate.health.dto.DonationRequestDTO;
import com.health_donate.health.service.DonationRequestService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("demande-dons")
@AllArgsConstructor
public class DonationRequestController {

    private DonationRequestService donationRequestService;


    //  CREATE
    @PostMapping("/create")
    public ResponseEntity<ApiResponse<DonationRequestDTO>> createDonationRequest(@RequestBody DonationRequestDTO dto) {
        DonationRequestDTO saved = donationRequestService.createDonationRequest(dto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>("201", "Demande de don créée avec succès", saved));
    }

    //  READ (Get by ID)
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<DonationRequestDTO>> getDonationRequestById(@PathVariable Long id) {
        DonationRequestDTO dto = donationRequestService.getDonationRequestById(id);
        if (dto == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>("404", "Demande de don non trouvée", null));
        }
        return ResponseEntity.ok(new ApiResponse<>("200", "Demande de don trouvée", dto));
    }

    //  READ ALL
    @GetMapping("/all")
    public ResponseEntity<ApiResponse<List<DonationRequestDTO>>> getAllDonationRequests() {
        List<DonationRequestDTO> requests = donationRequestService.getAllDonationRequests();
        return ResponseEntity.ok(new ApiResponse<>("200", "Liste des demandes de dons", requests));
    }

    //  UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<DonationRequestDTO>> updateDonationRequest(
            @PathVariable Long id,
            @RequestBody DonationRequestDTO dto
    ) {
        DonationRequestDTO updated = donationRequestService.updateDonationRequest(id, dto);
        if (updated == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>("404", "Demande de don à mettre à jour non trouvée", null));
        }
        return ResponseEntity.ok(new ApiResponse<>("200", "Demande de don mise à jour avec succès", updated));
    }

    //  DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Boolean>> deleteDonationRequest(@PathVariable Long id) {
        boolean deleted = donationRequestService.deleteDonationRequest(id);
        if (!deleted) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>("404", "Demande de don non trouvée", false));
        }
        return ResponseEntity.ok(new ApiResponse<>("200", "Demande de don supprimée avec succès", true));
    }



}
