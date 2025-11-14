package com.health_donate.health.controller;


import com.health_donate.health.dto.*;
import com.health_donate.health.entity.Actor;
import com.health_donate.health.entity.RefreshToken;
import com.health_donate.health.entity.User;
import com.health_donate.health.repository.ActorRepository;
import com.health_donate.health.repository.DonationRequestRepository;
import com.health_donate.health.repository.RoleRepository;
import com.health_donate.health.repository.UserRepository;
import com.health_donate.health.security.jwt.JwtService;
import com.health_donate.health.service.*;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("auth")
@AllArgsConstructor
    public class AuthController {


    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final RefreshTokenService refreshTokenService;
    private final ActorService actorService;
    private final AuthService authService;
    private final SocialActionService socialActionService;
    private DonationService donationService;
    private ImageService imageService;
    private final AssociationService associationService;

    private DonationRequestService donationRequestService;
    private DonationRequestRepository donationRequestRepository;


    // LOGIN
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<?>> login(@RequestBody LoginDTO request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getPhone(),
                        request.getPassword()
                )
        );

        Actor user = (Actor) userRepository.findByPhoneNumber(request.getPhone())
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

        String accessToken = jwtService.generateToken(user);
        RefreshToken refreshToken = refreshTokenService.createRefreshToken(user);

        return ResponseEntity.ok(
                new ApiResponse<>("200", "Connexion réussie", Map.of(
                        "access_token", accessToken,
                        "refresh_token", refreshToken.getToken()
                ))
        );
    }

    // LOGIN
    @PostMapping("/loginAdmin")
    public ResponseEntity<ApiResponse<?>> loginAdmin(@RequestBody LoginAdminDTO request) {

//        Actor user = actorRepository.findByEmail(request.getUsername()).orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

        String accessToken = authService.authenticateByEmail(request.getUsername(), request.getPassword());
//        RefreshToken refreshToken = refreshTokenService.createRefreshToken(user);

        return ResponseEntity.ok(
                new ApiResponse<>("200", "Connexion réussie", Map.of(
                        "access_token", accessToken
//                        "refresh_token", refreshToken.getToken()
                ))
        );
    }

    // REFRESH
    @PostMapping("/refresh-token")
    public ResponseEntity<ApiResponse<?>> refreshToken(@RequestBody Map<String, String> request) {
        String requestToken = request.get("refresh_token");

        RefreshToken refreshToken = refreshTokenService.getByToken(requestToken)
                .orElseThrow(() -> new RuntimeException("Refresh token invalide"));

        refreshTokenService.verifyExpiration(refreshToken);

        User user = refreshToken.getUser();
        String accessToken = jwtService.generateToken(user);

        return ResponseEntity.ok(new ApiResponse<>("200", "Access token renouvelé", Map.of(
                "access_token", accessToken
        )));
    }

    // LOGOUT
    @PostMapping("/logout")
    public ResponseEntity<ApiResponse<?>> logout(@RequestBody Map<String, String> request) {
        String phone = request.get("phone");
        User user = (User) userRepository.findByPhoneNumber(phone)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

        refreshTokenService.deleteByUser(user);

        return ResponseEntity.ok(new ApiResponse<>("200", "Déconnexion réussie", null));
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<?>> register(@RequestBody RegisterDTO request) {




        return ResponseEntity.ok(
                new ApiResponse<>("200", "Creation réussie",
                        this.actorService.createActor(request))
        );
    }

    @GetMapping("/action-socials")
    public Page<SocialActionDTO> getAllSocialActionsPaged(
            @RequestParam(defaultValue = "0") int page
    ) {
        return socialActionService.getAllSocialActionsPaged(page);
    }

    @GetMapping("/all")
    public Page<DonationDTO> getAllDonationsPaged(
            @RequestParam(defaultValue = "0") int page) {
        return donationService.getAllDonationsPaged(page);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ImageDTO>> getImageById(@PathVariable Long id) {
        ImageDTO dto = imageService.getImageById(id);
        if (dto == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>("404", "Image non trouvée", null));
        }
        return ResponseEntity.ok(new ApiResponse<>("200", "Image trouvée", dto));
    }

    @GetMapping("/asso/{id}")
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

    //Create association etant admin:
    @PostMapping("associations/create")
    public ResponseEntity<ApiResponse<?>> create(
            @RequestPart("contenu") AssociationDTO association,
            @RequestPart(value = "profil", required = false) MultipartFile logo,
            @RequestPart(value = "cover", required = false) MultipartFile coverFile
    ) throws IOException {
        AssociationDTO created = associationService.createAssociation(association,logo,coverFile);

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(
                new ApiResponse<>(
                        String.valueOf(HttpStatus.ACCEPTED.value()),
                        HttpStatus.ACCEPTED.getReasonPhrase(),
                        created
                ));
    }

    @PutMapping("associations/{id}")
    public ResponseEntity<AssociationDTO> update(
            @PathVariable Long id,
            @RequestPart("contenu") AssociationDTO association,
            @RequestPart(value = "profil", required = false) MultipartFile logo,
            @RequestPart(value = "cover", required = false) MultipartFile coverFile
    ) throws IOException {
        return ResponseEntity.ok(associationService.updateAssociation(id, association, logo,coverFile));
    }

    //Pour changer l'etat d'une association :
    @PutMapping("associations/statut/{id}")
    public ResponseEntity<?> updateStatut(
            @PathVariable Long id,
            @RequestPart("statut") String motif
    ){
        return ResponseEntity.ok(associationService.updateStatut(id,motif));
    }



    //Pour la suppression d'une association :
    @DeleteMapping("associations/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        return associationService.deleteAssociation(id)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }



    @GetMapping("associations/list")
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


    @GetMapping("associations/{id}")
    public ResponseEntity<ApiResponse<?>> getAssoById(@PathVariable Long id) {
        AssociationDTO associationDTO = associationService.getAssociationById(id);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(
                new ApiResponse<>(
                        String.valueOf(HttpStatus.ACCEPTED.value()),
                        HttpStatus.ACCEPTED.getReasonPhrase(),
                        associationDTO
                )
        );
    }



    @PostMapping("/demande-dons/")
    public ResponseEntity<ApiResponse<DonationRequestDTO>> createDonation(@RequestPart("demandeDon") DonationRequestDTO dto) {
        DonationRequestDTO saved = donationRequestService.createDemandeDon(dto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>("201", "Demande de don créée avec succès", saved));
    }

    //  assigner un don a quelqu'un
    @PutMapping("/demande-dons/{id}/{userID}")
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

