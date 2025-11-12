package com.health_donate.health.controller;

import com.health_donate.health.dto.DemandeAdhesionDTO;
import com.health_donate.health.dto.DemandeAdhesionRequest;
import com.health_donate.health.service.DemandeAdhesionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("demandes")
@RequiredArgsConstructor
public class DemandeAdhesionController {

    private final DemandeAdhesionService demandeAdhesionService;

    @PostMapping("/create")
    public ResponseEntity<DemandeAdhesionDTO> faireDemande(
            @RequestBody DemandeAdhesionRequest request
    ) {
        return ResponseEntity.ok(demandeAdhesionService.faireDemande(
                request.getUserId(),
                request.getAssociationId(),
                request.getMessage()
        ));
    }

    @GetMapping("/attente/{associationId}")
    public ResponseEntity<List<DemandeAdhesionDTO>> demandesEnAttente(@PathVariable Long associationId) {
        return ResponseEntity.ok(demandeAdhesionService.demandesEnAttente(associationId));
    }
    @GetMapping("/accepter/{associationId}")
    public ResponseEntity<List<DemandeAdhesionDTO>> demandesAccepter(@PathVariable Long associationId) {
        return ResponseEntity.ok(demandeAdhesionService.demandesAccepter(associationId));
    }
    @GetMapping("/rejeter/{associationId}")
    public ResponseEntity<List<DemandeAdhesionDTO>> demandesRejeter(@PathVariable Long associationId) {
        return ResponseEntity.ok(demandeAdhesionService.demandesRejeter(associationId));
    }

    @PostMapping("/accepter/{demandeId}")
    public ResponseEntity<DemandeAdhesionDTO> accepter(@PathVariable Long demandeId) {
        return ResponseEntity.ok(demandeAdhesionService.accepterDemande(demandeId));
    }

    @PostMapping("/rejeter/{demandeId}")
    public ResponseEntity<DemandeAdhesionDTO> rejeter(@PathVariable Long demandeId) {
        return ResponseEntity.ok(demandeAdhesionService.rejeterDemande(demandeId));
    }
}

