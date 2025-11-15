package com.health_donate.health.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.health_donate.health.dto.DonationDTO;
import com.health_donate.health.dto.OngDTO;
import com.health_donate.health.service.OngService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("ong")
@RequiredArgsConstructor
public class OngController {

    private final OngService ongService;
    private final ObjectMapper objectMapper;

    @GetMapping
    public ResponseEntity<List<OngDTO>> getAll() {
        return ResponseEntity.ok(ongService.getAllOngs());
    }

    @GetMapping("{id}")
    public ResponseEntity<OngDTO> getById(@PathVariable Long id) {
        OngDTO dto = ongService.getById(id);
        return dto != null ? ResponseEntity.ok(dto) : ResponseEntity.notFound().build();
    }

    @GetMapping("all")
    public Page<OngDTO> getAllOngsPaged(@RequestParam(defaultValue = "0") int page) {
        return ongService.getAllOngsPaged(page);
    }

    @PostMapping
    public ResponseEntity<OngDTO> create(
            @RequestPart("contenu") String dto,
            @RequestPart(value = "profil", required = false) MultipartFile profilFile,
            @RequestPart(value = "cover", required = false) MultipartFile coverFile
    ) throws IOException {

        OngDTO ongDTO = objectMapper.readValue(dto, OngDTO.class);
        return ResponseEntity.ok(ongService.createOng(ongDTO, profilFile, coverFile));
    }


    @PutMapping("{id}")
    public ResponseEntity<OngDTO> update(
            @PathVariable Long id,
            @RequestPart("contenu") OngDTO dto,
            @RequestPart(value = "profil", required = false) MultipartFile profilFile,
            @RequestPart(value = "cover", required = false) MultipartFile coverFile
            ) throws IOException {
        return ResponseEntity.ok(ongService.updateOng(id, dto, profilFile,coverFile));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        return ongService.deleteOng(id)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }
}
