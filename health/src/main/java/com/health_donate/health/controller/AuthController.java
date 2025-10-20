package com.health_donate.health.controller;


import com.health_donate.health.dto.ApiResponse;
import com.health_donate.health.dto.LoginAdminDTO;
import com.health_donate.health.dto.LoginDTO;
import com.health_donate.health.dto.RegisterDTO;
import com.health_donate.health.entity.Actor;
import com.health_donate.health.entity.RefreshToken;
import com.health_donate.health.entity.User;
import com.health_donate.health.repository.ActorRepository;
import com.health_donate.health.repository.RoleRepository;
import com.health_donate.health.repository.UserRepository;
import com.health_donate.health.security.jwt.JwtService;
import com.health_donate.health.service.ActorService;
import com.health_donate.health.service.AuthService;
import com.health_donate.health.service.RefreshTokenService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("auth")
@AllArgsConstructor
    public class AuthController {


    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final RefreshTokenService refreshTokenService;
    private final ActorService actorService;
    private final AuthService authService;
    private final ActorRepository actorRepository;

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
    @PostMapping("/refresh")
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
    }

