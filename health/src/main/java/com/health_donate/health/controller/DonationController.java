package com.health_donate.health.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.health_donate.health.dto.ApiResponse;
import com.health_donate.health.dto.DonationDTO;
import com.health_donate.health.entity.Donation;
import com.health_donate.health.repository.DonationRepository;
import com.health_donate.health.service.DonationService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.DataInput;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("dons")
@AllArgsConstructor
public class DonationController {

    private  DonationService donationService;
    private ObjectMapper objectMapper;
    private DonationRepository donationRepository;

    @PostMapping( consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse<?>> createDonation(
            @RequestPart("donation") String donationJson,
            @RequestPart("images") MultipartFile[] images
    ) throws IOException {



        DonationDTO donationDTO = objectMapper.readValue(donationJson, DonationDTO.class);


        if (images.length != 4) {
            return ResponseEntity.badRequest().body(
                    new ApiResponse<>(
                            String.valueOf(HttpStatus.BAD_REQUEST.value()),
                            "Vous devez envoyer exactement 4 images.",
                            null
                    )
            );
        }

        DonationDTO created = donationService.createDonationWithImages(donationDTO, images);

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(
                new ApiResponse<>(
                        String.valueOf(HttpStatus.OK.value()),
                        HttpStatus.OK.getReasonPhrase(),
                        created
                        ));

    }



    @GetMapping
    public ResponseEntity<ApiResponse<?>> getAllDonations() {
        List<DonationDTO> donations = donationService.getAllDonations();
        return ResponseEntity.ok(
                new ApiResponse<>(
                        String.valueOf(HttpStatus.OK.value()),
                        "Liste des dons récupérée avec succès",
                        donations
                )
        );
    }


    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<?>> getDonationById(@PathVariable Long id) {
        DonationDTO donation = donationService.getDonationById(id);
        if (donation == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ApiResponse<>(
                            String.valueOf(HttpStatus.NOT_FOUND.value()),
                            "Don introuvable pour l'id " + id,
                            null
                    )
            );
        }

        return ResponseEntity.ok(
                new ApiResponse<>(
                        String.valueOf(HttpStatus.OK.value()),
                        "Don récupéré avec succès",
                        donation
                )
        );
    }


    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse<?>> updateDonation(
            @PathVariable Long id,
            @RequestPart("donation") String donationJson,
            @RequestPart(value = "images", required = false) MultipartFile[] images
    ) throws IOException {

        DonationDTO donationDTO = objectMapper.readValue(donationJson, DonationDTO.class);
        DonationDTO updatedDonation = donationService.updateDonation(id, donationDTO, images);

        return ResponseEntity.ok(
                new ApiResponse<>(
                        String.valueOf(HttpStatus.OK.value()),
                        "Don mis à jour avec succès",
                        updatedDonation
                )
        );
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<?>> deleteDonation(@PathVariable Long id) {
        boolean deleted = donationService.deleteDonation(id);
        if (!deleted) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ApiResponse<>(
                            String.valueOf(HttpStatus.NOT_FOUND.value()),
                            "Aucun don trouvé pour l'id " + id,
                            null
                    )
            );
        }

        return ResponseEntity.ok(
                new ApiResponse<>(
                        String.valueOf(HttpStatus.OK.value()),
                        "Don supprimé avec succès",
                        null
                )
        );
    }


    @GetMapping("/dons/last4")
    public ResponseEntity<List<Donation>> getLastFourDonations() {
        List<Donation> donations = donationRepository
                .findTop4ByOrderByIdDesc(); // ou OrderByCreatedAtDesc si tu as un champ date
        return ResponseEntity.ok(donations);
    }





}
