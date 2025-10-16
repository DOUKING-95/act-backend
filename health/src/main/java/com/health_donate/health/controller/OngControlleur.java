package com.health_donate.health.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.health_donate.health.dto.ApiResponse;
import com.health_donate.health.dto.OngDTO;
import com.health_donate.health.service.OngService;
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
@RequestMapping("auth")
@RequiredArgsConstructor
public class OngControlleur {



    private final OngService ongService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse<?>> createOng(
            @RequestPart("ong") String ongJson,
            @RequestPart(value = "logo", required = false) MultipartFile logo
    ) throws IOException {


        ObjectMapper objectMapper = new ObjectMapper();
        OngDTO dto = objectMapper.readValue(ongJson, OngDTO.class);

        OngDTO created = ongService.createOng(dto, logo);

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(
                new ApiResponse<>(
                        String.valueOf(HttpStatus.ACCEPTED.value()),
                        HttpStatus.ACCEPTED.getReasonPhrase(),
                        created
                ));


    }
}
