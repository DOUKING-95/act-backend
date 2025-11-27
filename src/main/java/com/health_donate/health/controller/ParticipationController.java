package com.health_donate.health.controller;


import com.google.zxing.WriterException;
import com.health_donate.health.dto.ApiResponse;
import com.health_donate.health.dto.ParticipationDTO;
import com.health_donate.health.dto.TopParticipantDTO;
import com.health_donate.health.repository.ParticipationRepository;
import com.health_donate.health.service.ParticipationService;
import com.health_donate.health.service.SocialActionService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("participations")
@AllArgsConstructor
public class ParticipationController {

    private final ParticipationService participationService;
    private final ParticipationRepository participationRepository;




    @GetMapping(path = "countActiviteByUser/{donorId}")
    public ResponseEntity<ApiResponse<?>> getAssociationCounrrByUser(
            @PathVariable Long donorId


    ){

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(
                new ApiResponse<>(
                        String.valueOf(HttpStatus.OK.value()),
                        HttpStatus.OK.getReasonPhrase(),
                        participationRepository.countByActorIdAndStatus(donorId, true)
                ));

    }


    // --- CREATE ---
    @PostMapping
    public ResponseEntity<ParticipationDTO> createParticipation(
            @Valid @RequestBody ParticipationDTO dto) {
        ParticipationDTO created = participationService.createParticipation(dto);
        return ResponseEntity.ok(created);
    }

    // --- READ ALL ---
    @GetMapping
    public ResponseEntity<List<ParticipationDTO>> getAllParticipations() {
        // Tu peux ajouter un service pour fetch all si besoin
        return ResponseEntity.ok(participationService.getAllParticipations());
    }

    // --- READ BY ID ---
    @GetMapping("/{id}")
    public ResponseEntity<ParticipationDTO> getParticipationById(@PathVariable Long id) {
        ParticipationDTO participation = participationService.getParticipationById(id);
        if (participation == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(participation);
    }

    // --- UPDATE ---
    @PutMapping("/{id}")
    public ResponseEntity<ParticipationDTO> updateParticipation(
            @PathVariable Long id,
            @Valid @RequestBody ParticipationDTO dto) {
        ParticipationDTO updated = participationService.updateParticipation(id, dto);
        if (updated == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updated);
    }

    // --- DELETE ---
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteParticipation(@PathVariable Long id) {
        boolean deleted = participationService.deleteParticipation(id);
        if (!deleted) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }




    @GetMapping("/top-participants")
    public ResponseEntity<List<TopParticipantDTO>> getTopParticipants() {
        return ResponseEntity.ok(participationService.getTop15Participants());
    }


    // Vérifier si l'acteur a déjà participé
    @GetMapping("/has-participated")
    public ResponseEntity<Boolean> hasParticipated(
            @RequestParam Long actorId,
            @RequestParam Long activiteId) {
        return ResponseEntity.ok(participationService.hasParticipated(actorId, activiteId));
    }

    // Compter les participations pour une activité
    @GetMapping("/count")
    public ResponseEntity<Long> count(
            @RequestParam Long activiteId) {
        return ResponseEntity.ok(participationService.countParticipations(activiteId));
    }




    // GET PNG image (application/octet-stream) — requires auth/admin ideally
    @GetMapping(value = "/generate/{activiteId}", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<byte[]> generateQRCode(@PathVariable Long activiteId,
                                                 @RequestParam(defaultValue = "false") boolean singleUse) {
        try {
            byte[] png = participationService.generateQRCodeForActivity(activiteId, singleUse);
            return ResponseEntity.ok().contentType(MediaType.IMAGE_PNG).body(png);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    // Regenerate (admin)
    @PostMapping("/regenerate/{activiteId}")
    public ResponseEntity<?> regenerate(@PathVariable Long activiteId,
                                        @RequestParam(defaultValue = "false") boolean singleUse) {
        try {
            byte[] png = participationService.regenerateQRCode(activiteId, singleUse);
            String base64 = Base64.getEncoder().encodeToString(png);
            return ResponseEntity.ok(Map.of("qrBase64", base64));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error", e.getMessage()));
        }
    }

    // Validate scan (this is the endpoint Flutter calls)
    @PostMapping("/validate")
    public ResponseEntity<?> validate(@RequestParam String code, @RequestParam Long actorId) {
        try {
            ParticipationDTO dto = participationService.validatePresence(actorId, code);
            return ResponseEntity.ok(Map.of("success", true, "data", dto));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("success", false, "message", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("success", false, "message", "Erreur serveur"));
        }
    }

}

