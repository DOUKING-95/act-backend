package com.health_donate.health.service;

import com.health_donate.health.entity.User;
import com.health_donate.health.repository.UserRepository;
import com.health_donate.health.security.jwt.JwtService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public AuthService(UserRepository userRepository, AuthenticationManager authenticationManager, JwtService jwtService) {
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    public String authenticateByEmail(String email, String password) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));

        authenticate(email, password);

        return jwtService.generateToken(user);
    }

    public String authenticateByPhoneNumber(String phoneNumber, String password) {

        User user = userRepository.findByPhoneNumber(phoneNumber)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with phone: " + phoneNumber));

        authenticate(phoneNumber, password);
        return jwtService.generateToken(user);
    }

    private void authenticate(String loginField, String password) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginField, password)
        );
    }
}
