package com.health_donate.health.controller;

import com.health_donate.health.service.external.EmailSender;
import com.health_donate.health.service.external.EmailTemplateService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("email")
@AllArgsConstructor
public class EmailController {


    private EmailSender emailService;


    private EmailTemplateService templateService;

    @PostMapping("/send-partnership")
    public ResponseEntity<String> sendPartnershipEmail(@RequestParam String to,
                                                       @RequestParam String name) {
        try {
            String html = templateService.loadTemplate(name);

            emailService.sendHtmlEmail(to,
                    "Partenariat stratégique avec HealthDonate",
                    html);

            return ResponseEntity.ok("Email envoyé !");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Erreur : " + e.getMessage());
        }
    }
}

