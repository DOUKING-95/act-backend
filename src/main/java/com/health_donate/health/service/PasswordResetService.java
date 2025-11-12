package com.health_donate.health.service;

import com.health_donate.health.entity.PasswordResetToken;
import com.health_donate.health.entity.User;
import com.health_donate.health.repository.PasswordResetTokenRepository;
import com.health_donate.health.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PasswordResetService {

    private final UserRepository userRepository;
    private final PasswordResetTokenRepository tokenRepository;
    private final JavaMailSender mailSender;
    private final PasswordEncoder passwordEncoder;

    public void sendResetLink(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

        String token = UUID.randomUUID().toString();
        PasswordResetToken resetToken = new PasswordResetToken();
        resetToken.setToken(token);
        resetToken.setUser(user);
        resetToken.setExpirationDate(LocalDateTime.now().plusHours(1)); // expire dans 1h
        tokenRepository.save(resetToken);

        String resetLink = "http://localhost:8085/api/v1/act-org/auth/reset-password?token=" + token;

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Réinitialisation de votre mot de passe");
        message.setText("Cliquez sur le lien pour réinitialiser votre mot de passe :\n" + resetLink);
        mailSender.send(message);
    }

    public void resetPassword(String token, String newPassword) {
        PasswordResetToken resetToken = tokenRepository.findByToken(token)
                .orElseThrow(() -> new RuntimeException("Token invalide"));

        if (resetToken.getExpirationDate().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Le token a expiré");
        }

        User user = resetToken.getUser();
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);

        tokenRepository.delete(resetToken); // supprimer le token après usage
    }
}

