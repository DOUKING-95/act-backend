package com.health_donate.health.controller;

import com.health_donate.health.dto.ActorDTO;
import com.health_donate.health.dto.ApiResponse;
import com.health_donate.health.dto.DTOAdmin.DonDTO;
import com.health_donate.health.dto.DonationDTO;
import com.health_donate.health.dto.UserDTO;
import com.health_donate.health.entity.User;
import com.health_donate.health.service.DonService;
import com.health_donate.health.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("auth/donations")
@RequiredArgsConstructor
public class DonController {

    private final DonService donationService;

    private final UserService userService;

    //CREATE DONATION (avec images)
    @PostMapping(path = "/",consumes = {"multipart/form-data"})
    public ResponseEntity<DonDTO> createDonation(
            @RequestPart("donation") DonDTO donationDTO,
            @RequestPart(value = "images", required = false) MultipartFile[] images) {
        try {
            DonDTO created = donationService.createDonationWithImages(donationDTO, images);
            return ResponseEntity.status(HttpStatus.CREATED).body(created);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    //GET ALL DONATIONS
    @GetMapping("/")
    public ResponseEntity<ApiResponse<?>> getAllDonations() {
        List<DonDTO> donations = donationService.getAllDonations();
        return ResponseEntity.ok(
                new ApiResponse<>(
                        String.valueOf(HttpStatus.OK.value()),
                        "Liste des dons récupérée avec succès",
                        donations
                )
        );
    }

    //GET ONE DONATION BY ID
    @GetMapping("/{id}")
    public ResponseEntity<DonDTO> getDonationById(@PathVariable Long id) {
        try {
            DonDTO donation = donationService.getDonationById(id);
            return ResponseEntity.ok(donation);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    //UPDATE DONATION (avec images)
    @PutMapping(value = "/{id}", consumes = {"multipart/form-data"})
    public ResponseEntity<DonDTO> updateDonation(
            @PathVariable Long id,
            @RequestPart("donation") DonDTO donationDTO,
            @RequestPart(value = "images", required = false) MultipartFile[] images) {
        try {
            DonDTO updated = donationService.updateDonation(id, donationDTO, images);
            return ResponseEntity.ok(updated);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }


    //Publier un don
    @PutMapping("/statut/{id}/Publie")
    public ResponseEntity<DonDTO> publishDonation(@PathVariable Long id) {
        DonDTO updated = donationService.publishDonation(id);
        return ResponseEntity.ok(updated);
    }

    //Décliner un don avec une raison
    @PutMapping("/statut/{id}/Decline")
    public ResponseEntity<DonDTO> declineDonation(
            @PathVariable Long id,
            @RequestParam("raison") String reason) {

        DonDTO updated = donationService.declineDonation(id, reason);
        return ResponseEntity.ok(updated);
    }

    //Revoque un don avec une raison
    @PutMapping("/statut/{id}/Revoque")
    public ResponseEntity<DonDTO> revoqueDonation(
            @PathVariable Long id,
            @RequestParam("raison") String reason) {

        DonDTO updated = donationService.revoqueDonation(id, reason);
        return ResponseEntity.ok(updated);
    }

    //Attribuer un don à un bénéficiaire et marquer comme livré
    @PutMapping("/statut/{id}/assign")
    public ResponseEntity<DonDTO> assignAndDeliverDonation(
            @PathVariable Long id,
            @RequestParam("beneficiaryId") Long beneficiaryId) {
        DonDTO updated = donationService.assignAndDeliverDonation(id, beneficiaryId);
        return ResponseEntity.ok(updated);
    }

    //Modifier une attribution (changer bénéficiaire ou statut)
    @PutMapping("/statut/{id}/reassign")
    public ResponseEntity<DonDTO> modifyAssignment(
            @PathVariable Long id,
            @RequestParam("newBeneficiaryId") Long newBeneficiaryId) {
        DonDTO updated = donationService.modifyAssignment(id, newBeneficiaryId);
        return ResponseEntity.ok(updated);
    }

    //Notifier le donateur ou bénéficiaire (placeholder)
    @PostMapping("/statut/{id}/Notifie/{donorID}")
    public ResponseEntity<DonDTO> notifyDonation(@PathVariable Long id,@PathVariable Long donorID,
                                                 @RequestParam("message") String message) {
        ;
        return ResponseEntity.ok(donationService.notifyDonation(id,message,donorID));
    }



    //DELETE DONATION
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDonation(@PathVariable Long id) {
        boolean deleted = donationService.deleteDonation(id);
        if (deleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    //Pour la recuperation de la liste des utilisateurs du systeme
    @GetMapping("/users")
    public ResponseEntity<List<UserDTO>> getAllUser(){
        return ResponseEntity.ok(userService.getAllUsers());
    }
}
