package com.health_donate.health.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.health_donate.health.dto.ApiResponse;
import com.health_donate.health.dto.AssociationDTO;
import com.health_donate.health.dto.OngDTO;
import com.health_donate.health.enumT.DonationStatus;
import com.health_donate.health.repository.AssociationRepository;
import com.health_donate.health.service.AssociationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/associations")
@RequiredArgsConstructor
public class AssociationControlleur {

    private final AssociationService associationService;
    private final AssociationRepository associationRepository;


    @GetMapping("/associations")
    public Page<AssociationDTO> getAllAssociationsPaged(
            @RequestParam(defaultValue = "0") int page
    ) {
        return associationService.getAllAssociationsPaged(page);
    }


    @GetMapping(path = "countAssociationByUser/{donorId}")
    public ResponseEntity<ApiResponse<?>> getAssociationCounrrByUser(
            @PathVariable Long donorId


    ){

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(
                new ApiResponse<>(
                        String.valueOf(HttpStatus.OK.value()),
                        HttpStatus.OK.getReasonPhrase(),
                        associationRepository.countByUserId(donorId)
                ));

    }


    @GetMapping("/users/{userId}")
    public Page<AssociationDTO> getAssociationsByUserPaged(
            @PathVariable Long userId,
            @RequestParam(defaultValue = "0") int page
    ) {
        return associationService.getAssociationsByUserPaged(userId, page);
    }

    @GetMapping("user/{userId}")
    public ResponseEntity<ApiResponse<?>> getAssociationsByUserId(@PathVariable Long userId) {


        return ResponseEntity.ok(
                new ApiResponse<>(
                        String.valueOf(HttpStatus.OK.value()),
                        "Association récupéré avec succès",
                        associationService.getAssociationById(userId)
                )
        );

    }


    @PostMapping( consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse<?>> createAssociation(
            @RequestPart("association") String associationJson,
            @RequestPart(value = "logo", required = false) MultipartFile logo,
            @RequestPart(value = "cover", required = false) MultipartFile cover
    ) throws IOException {


        ObjectMapper objectMapper = new ObjectMapper();
        AssociationDTO dto = objectMapper.readValue(associationJson, AssociationDTO.class);

        AssociationDTO created = associationService.createAssociation(dto, logo, cover);

        return ResponseEntity.status(HttpStatus.OK).body(
                new ApiResponse<>(
                        String.valueOf(HttpStatus.OK.value()),
                        HttpStatus.OK.getReasonPhrase(),
                        created
                ));


    }


}
