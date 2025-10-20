package com.health_donate.health.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.health_donate.health.dto.ApiResponse;
import com.health_donate.health.dto.DonationDTO;
import com.health_donate.health.service.DonationService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.DataInput;
import java.io.IOException;

@RestController
@RequestMapping("dons")
@AllArgsConstructor
public class DonationController {

    private  DonationService donationService;
    private ObjectMapper objectMapper;

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
                        String.valueOf(HttpStatus.ACCEPTED.value()),
                        HttpStatus.ACCEPTED.getReasonPhrase(),
                        created
                        ));

    }

}
