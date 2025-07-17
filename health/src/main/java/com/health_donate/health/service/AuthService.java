package com.health_donate.health.service;


import com.health_donate.health.dto.AuthResponse;
import com.health_donate.health.dto.LoginRequest;
import com.health_donate.health.dto.LoginResponse;
import com.health_donate.health.dto.RegisterRequest;

public interface AuthService {
    LoginResponse register(RegisterRequest request);
    LoginResponse login(LoginRequest request);
}

