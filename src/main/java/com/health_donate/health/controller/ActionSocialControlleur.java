package com.health_donate.health.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.health_donate.health.dto.ApiResponse;
import com.health_donate.health.dto.SocialActionDTO;
import com.health_donate.health.service.SocialActionService;
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
@RequestMapping("action-socials")
@RequiredArgsConstructor
public class ActionSocialControlleur {


    private final SocialActionService socialActionService;
    private final ObjectMapper objectMapper;

    @GetMapping("/action-socials")
    public Page<SocialActionDTO> getAllSocialActionsPaged(
            @RequestParam(defaultValue = "0") int page
    ) {
        return socialActionService.getAllSocialActionsPaged(page);
    }


    @GetMapping("/by-association/{associationId}")
    public Page<SocialActionDTO> getActivitiesByAssociation(
            @PathVariable Long associationId,
            @RequestParam(defaultValue = "0") int page // page = 0 par défaut
    ) {
        return socialActionService.getActivitiesByAssociation(associationId, page);
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse<?>> createSocialAction(
            @RequestPart("socialAction") String socialActionJson,
            @RequestPart(value = "images", required = false) MultipartFile[] images
    ) throws IOException {


        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        SocialActionDTO dto = objectMapper.readValue(socialActionJson, SocialActionDTO.class);

        SocialActionDTO created = socialActionService.createSocialAction(dto, images);

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(
                new ApiResponse<>(
                        String.valueOf(HttpStatus.ACCEPTED.value()),
                        HttpStatus.ACCEPTED.getReasonPhrase(),
                        created
                ));


    }


    //  GET ALL
    @GetMapping
    public ResponseEntity<ApiResponse<?>> getAllSocialActions() {
        List<SocialActionDTO> actions = socialActionService.getAllSocialActions();

        return ResponseEntity.ok(
                new ApiResponse<>(
                        String.valueOf(HttpStatus.OK.value()),
                        "Liste des actions sociales récupérée avec succès",
                        actions
                )
        );
    }

    //  GET BY ID
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<?>> getSocialActionById(@PathVariable Long id) {
        SocialActionDTO action = socialActionService.getSocialActionById(id);

        if (action == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ApiResponse<>(
                            String.valueOf(HttpStatus.NOT_FOUND.value()),
                            "Aucune action sociale trouvée pour cet ID",
                            null
                    )
            );
        }

        return ResponseEntity.ok(
                new ApiResponse<>(
                        String.valueOf(HttpStatus.OK.value()),
                        "Action sociale récupérée avec succès",
                        action
                )
        );
    }

    //  UPDATE
    @PutMapping(path = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse<?>> updateSocialAction(
            @PathVariable Long id,
            @RequestPart("action") String actionJson,
            @RequestPart(value = "images", required = false) MultipartFile[] images
    ) throws IOException {

        SocialActionDTO dto = objectMapper.readValue(actionJson, SocialActionDTO.class);
        SocialActionDTO updated = socialActionService.updateSocialAction(id, dto, images);

        return ResponseEntity.ok(
                new ApiResponse<>(
                        String.valueOf(HttpStatus.OK.value()),
                        "Action sociale mise à jour avec succès",
                        updated
                )
        );
    }

    //  DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<?>> deleteSocialAction(@PathVariable Long id) {
        boolean deleted = socialActionService.deleteSocialAction(id);

        if (!deleted) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ApiResponse<>(
                            String.valueOf(HttpStatus.NOT_FOUND.value()),
                            "Aucune action sociale trouvée pour suppression",
                            null
                    )
            );
        }

        return ResponseEntity.ok(
                new ApiResponse<>(
                        String.valueOf(HttpStatus.OK.value()),
                        "Action sociale supprimée avec succès",
                        null
                )
        );
    }
}
