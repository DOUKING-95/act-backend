package com.health_donate.health.controller;

import com.health_donate.health.dto.ApiResponse;
import com.health_donate.health.service.UserService;
import com.health_donate.health.service.ValidationService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;


@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class ValidationController {

    private ValidationService validationService;

    @PostMapping( path = "/validation-utilsateur" ,consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse<?>> validationUtilsateur(
            @RequestBody Map<String, String> code
    ) {

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(
                new ApiResponse<>(
                        String.valueOf(HttpStatus.ACCEPTED.value()),
                        HttpStatus.ACCEPTED.getReasonPhrase(),
                        this.validationService.validationUtilsateur(code)));
    }
}
