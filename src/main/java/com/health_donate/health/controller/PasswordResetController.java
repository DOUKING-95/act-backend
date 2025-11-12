package com.health_donate.health.controller;

import com.health_donate.health.service.PasswordResetService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/act-org/auth")
@RequiredArgsConstructor
public class PasswordResetController {

    private final PasswordResetService passwordResetService;

    @PostMapping("/forgot-password")
    public ResponseEntity<String> forgotPassword(@RequestParam String email) {
        passwordResetService.sendResetLink(email);
        return ResponseEntity.ok("Lien de réinitialisation envoyé à votre adresse email.");
    }

    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(
            @RequestParam String token,
            @RequestParam String newPassword) {
        passwordResetService.resetPassword(token, newPassword);
        return ResponseEntity.ok("Mot de passe réinitialisé avec succès !");
    }
}
