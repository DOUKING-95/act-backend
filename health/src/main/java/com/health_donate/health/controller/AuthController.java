package com.health_donate.health.controller;


import com.health_donate.health.dto.ApiResponse;
import com.health_donate.health.dto.LoginDTO;
import com.health_donate.health.dto.RegisterDTO;
import com.health_donate.health.entity.Actor;
import com.health_donate.health.entity.User;
import com.health_donate.health.repository.RoleRepository;
import com.health_donate.health.repository.UserRepository;
import com.health_donate.health.security.jwt.JwtService;
import com.health_donate.health.service.ActorService;
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


        private AuthenticationManager authenticationManager;
        private RoleRepository roleRepository;
        private ActorService actorService;
        private UserRepository userRepository;
        private JwtService jwtService;
        private PasswordEncoder passwordEncoder;


        @PostMapping("/register")
        public ResponseEntity<ApiResponse<?>> register(@RequestBody RegisterDTO request) {
            if (userRepository.findByPhoneNumber(request.phone()).isPresent()) {
                throw new RuntimeException("Ce numéro est déjà réconnu par le systeme Act ! Merci de renseignez un autre numéro .");
            }


            Actor user = new Actor();
            user.setName(request.name());
            user.setPhoneNumber(request.phone());
            user.setEmail(request.email());

            user.setPassword(passwordEncoder.encode(request.password()));


            actorService.createActor(request);

            String token = jwtService.generateToken(user);

            return ResponseEntity.status(HttpStatus.ACCEPTED).body(
                    new ApiResponse<>(
                            String.valueOf(HttpStatus.ACCEPTED.value()),
                            HttpStatus.ACCEPTED.getReasonPhrase(),
                            Map.of(token, "Inscription réussie !")));

        }


        @PostMapping("/login")
        public ResponseEntity<ApiResponse<?>> login(@RequestBody LoginDTO request) {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getPhone(),
                            request.getPassword()
                    )
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);
            User user = userRepository.findByPhoneNumber(request.getPhone())
                    .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé par son numero"));

            String token = jwtService.generateToken(user);


            return ResponseEntity.status(HttpStatus.ACCEPTED).body(
                    new ApiResponse<>(
                            String.valueOf(HttpStatus.ACCEPTED.value()),
                            HttpStatus.ACCEPTED.getReasonPhrase(),
                            Map.of(token, "Connexion réussie ! !")));

        }
    }

