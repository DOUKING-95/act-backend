package com.health_donate.health.service;



import com.health_donate.health.dto.LoginRequest;
import com.health_donate.health.dto.LoginResponse;
import com.health_donate.health.dto.RegisterRequest;
import com.health_donate.health.entity.User;
import com.health_donate.health.repository.RoleRepository;
import com.health_donate.health.repository.UserRepository;
import com.health_donate.health.security.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.management.relation.Role;
import java.util.Collections;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;



    @Override
    public LoginResponse register(RegisterRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new RuntimeException("Username already taken");
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        com.health_donate.health.entity.Role userRole = roleRepository.findByName("ROLE_USER")
                .orElseThrow(() -> new RuntimeException("Role not found"));
        user.setRoles(Collections.singleton(userRole));

        userRepository.save(user);

        String token = jwtUtils.generateToken(user.getUsername());
        return new LoginResponse(
                token,
                user.getUsername(),
                user.getEmail(),
                userRole.getName()
        );
    }

    @Override
    public LoginResponse login(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );

        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        String token = jwtUtils.generateToken(user.getUsername());
        String role = user.getRoles().stream().findFirst().map(Role::getName).orElse("ROLE_USER");

        return new LoginResponse(
                token,
                user.getUsername(),
                user.getEmail(),
                role
        );
    }

}

