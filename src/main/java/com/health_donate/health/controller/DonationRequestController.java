package com.health_donate.health.controller;

import com.health_donate.health.dto.ApiResponse;
import com.health_donate.health.dto.DonationRequestDTO;
import com.health_donate.health.enumT.RequestStatus;
import com.health_donate.health.repository.DonationRequestRepository;
import com.health_donate.health.service.DonationRequestService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("auth/demande-dons")
@AllArgsConstructor
public class DonationRequestController {

    private DonationRequestService donationRequestService;
    private DonationRequestRepository donationRequestRepository;



    @GetMapping("/requester/{requesterId}/approuve")
    public List<DonationRequestDTO> getRequestsByRequesterAndStatus(
            @PathVariable Long requesterId
           ) {
        return donationRequestService.getRequestsByRequesterAndStatus(requesterId);
    }

    @GetMapping("/requester/{requesterId}/approuve-pagine")
    public Page<DonationRequestDTO> getRequestsByRequesterAndStatus(
            @PathVariable Long requesterId,

            @RequestParam(defaultValue = "0") int page) {
        return donationRequestService.getRequestsByRequesterAndStatus(requesterId, page);
    }


    @GetMapping(path = "countDonRecuByUser/{donorId}")
    public ResponseEntity<ApiResponse<?>> getAssociationCounrrByUser(
            @PathVariable Long donorId


    ){

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(
                new ApiResponse<>(
                        String.valueOf(HttpStatus.OK.value()),
                        HttpStatus.OK.getReasonPhrase(),
                        donationRequestRepository.countByRequester_IdAndStatus(donorId, RequestStatus.APPROVED )
                ));

    }

    @PostMapping("/")
    public ResponseEntity<ApiResponse<DonationRequestDTO>> createDonation(@RequestPart("demandeDon") DonationRequestDTO dto) {
        DonationRequestDTO saved = donationRequestService.createDemandeDon(dto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>("201", "Demande de don créée avec succès", saved));
    }


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
            @RequestPart("accept") Boolean accept
    ) {
        DonationRequestDTO updated = donationRequestService.updateDonationRequest(id, accept);
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


    //  assigner un don a quelqu'un
    @PutMapping("/{id}/{userID}")
    public ResponseEntity<ApiResponse<DonationRequestDTO>> assignerDon(
            @PathVariable Long id,
            @PathVariable Long userID
    ) {
        DonationRequestDTO updated = donationRequestService.assignerDon(id, userID);
        if (updated == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>("404", "Don non trouvé/assigne", null));
        }
        return ResponseEntity.ok(new ApiResponse<>("200", "Don assigner avec succès", updated));
    }
}
