package com.health_donate.health.controller;

import com.health_donate.health.dto.DemandeAdhesionDTO;
import com.health_donate.health.service.DemandeAdhesionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("auth/demandes")
@RequiredArgsConstructor
public class DemandeAdhesionController {

    private final DemandeAdhesionService demandeAdhesionService;

    @PostMapping("/create")
    public ResponseEntity<DemandeAdhesionDTO> faireDemande(
            @RequestParam Long userId,
            @RequestParam Long associationId,
            @RequestParam(required = false) String message
    ) {
        return ResponseEntity.ok(demandeAdhesionService.faireDemande(userId, associationId, message));
    }

    @GetMapping("/attente/{associationId}")
    public ResponseEntity<List<DemandeAdhesionDTO>> demandesEnAttente(@PathVariable Long associationId) {
        return ResponseEntity.ok(demandeAdhesionService.demandesEnAttente(associationId));
    }

    @PutMapping("/accepter/{demandeId}")
    public ResponseEntity<DemandeAdhesionDTO> accepter(@PathVariable Long demandeId) {
        return ResponseEntity.ok(demandeAdhesionService.accepterDemande(demandeId));
    }

    @PutMapping("/rejeter/{demandeId}")
    public ResponseEntity<DemandeAdhesionDTO> rejeter(@PathVariable Long demandeId) {
        return ResponseEntity.ok(demandeAdhesionService.rejeterDemande(demandeId));
    }
}

