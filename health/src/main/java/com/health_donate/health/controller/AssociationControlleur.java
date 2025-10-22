package com.health_donate.health.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.health_donate.health.dto.ApiResponse;
import com.health_donate.health.dto.AssociationDTO;
import com.health_donate.health.dto.OngDTO;
import com.health_donate.health.service.AssociationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.DataInput;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("associations")
@RequiredArgsConstructor
public class AssociationControlleur {

    private final AssociationService associationService;



    @GetMapping("list")
    public ResponseEntity<ApiResponse<?>> allAssociation(){
        List<AssociationDTO> associationDTOList = associationService.allAssociations();

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(
                new ApiResponse<>(
                        String.valueOf(HttpStatus.ACCEPTED.value()),
                        HttpStatus.ACCEPTED.getReasonPhrase(),
                        associationDTOList
                )
        );
    }


    @GetMapping("{id}")
    public ResponseEntity<ApiResponse<?>> getById(@PathVariable Long id) {
        AssociationDTO associationDTO = associationService.getAssociationById(id);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(
                new ApiResponse<>(
                        String.valueOf(HttpStatus.ACCEPTED.value()),
                        HttpStatus.ACCEPTED.getReasonPhrase(),
                        associationDTO
                )
        );
    }

    @GetMapping("/user/{userId}")
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

    //Create association etant admin:
    @PostMapping("create")
    public ResponseEntity<ApiResponse<?>> create(
            @RequestPart("contenu") AssociationDTO association,
            @RequestPart(value = "fichier", required = false) MultipartFile logo) throws IOException {
        AssociationDTO created = associationService.createAssociation(association,logo,null);

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(
                new ApiResponse<>(
                        String.valueOf(HttpStatus.ACCEPTED.value()),
                        HttpStatus.ACCEPTED.getReasonPhrase(),
                        created
                ));
    }

    @PutMapping("{id}")
    public ResponseEntity<AssociationDTO> update(
            @PathVariable Long id,
            @RequestPart("contenu") AssociationDTO association,
            @RequestPart(value = "fichier", required = false) MultipartFile logo
    ) throws IOException {
        return ResponseEntity.ok(associationService.updateAssociation(id, association, logo,null));
    }

    //Pour changer l'etat d'une association :
    @PutMapping("statut/{id}")
    public ResponseEntity<?> updateStatut(
            @PathVariable Long id,
            @RequestPart("statut") String motif
    ){
        return ResponseEntity.ok(associationService.updateStatut(id,motif));
    }



    //Pour la suppression d'une association :
    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        return associationService.deleteAssociation(id)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }


}
