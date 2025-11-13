package com.health_donate.health.controller;

import com.health_donate.health.dto.ActorDTO;
import com.health_donate.health.dto.ApiResponse;
import com.health_donate.health.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("utilisateurs")
@AllArgsConstructor
public class UserController {

    private UserService userService;


    @GetMapping(path = "get_user_by_phone")
    public ResponseEntity<ApiResponse<?>> getUserByPhone(@RequestParam String phone) {

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(
                new ApiResponse<>(
                        String.valueOf(HttpStatus.ACCEPTED.value()),
                        HttpStatus.ACCEPTED.getReasonPhrase(),
                        this.userService.getUserByPhone(phone))
        );
    }
}
