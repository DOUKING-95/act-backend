package com.health_donate.health.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.health_donate.health.dto.ApiResponse;
import com.health_donate.health.dto.SocialActionDTO;
import com.health_donate.health.service.SocialActionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("action-socials")
@RequiredArgsConstructor
public class ActionSocialControlleur {


    private final SocialActionService socialActionService;



    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse<?>> createSocialAction(
            @RequestPart("socialAction") String socialActionJson,
            @RequestPart(value = "images", required = false) MultipartFile[] images
    ) throws IOException {


        ObjectMapper objectMapper = new ObjectMapper();
        SocialActionDTO dto = objectMapper.readValue(socialActionJson, SocialActionDTO.class);

        SocialActionDTO created = socialActionService.createSocialAction(dto, images);

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(
                new ApiResponse<>(
                        String.valueOf(HttpStatus.ACCEPTED.value()),
                        HttpStatus.ACCEPTED.getReasonPhrase(),
                        created
                ));


    }
}
