package com.health_donate.health.controller;


import com.health_donate.health.dto.OngDTO;
import com.health_donate.health.service.OngService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("auth/ong/")
@RequiredArgsConstructor
public class OngController {

    private final OngService ongService;

    @GetMapping
    public ResponseEntity<List<OngDTO>> getAll() {
        return ResponseEntity.ok(ongService.getAllOngs());
    }

    @GetMapping("{id}")
    public ResponseEntity<OngDTO> getById(@PathVariable Long id) {
        OngDTO dto = ongService.getById(id);
        return dto != null ? ResponseEntity.ok(dto) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<OngDTO> create(
            @RequestPart("contenu") OngDTO dto,
            @RequestPart(value = "fichier", required = false) MultipartFile logoFile) throws IOException {
        return ResponseEntity.ok(ongService.createOng(dto, logoFile));
    }

    @PutMapping("{id}")
    public ResponseEntity<OngDTO> update(
            @PathVariable Long id,
            @RequestPart("contenu") OngDTO dto,
            @RequestPart(value = "fichier", required = false) MultipartFile logoFile) throws IOException {
        return ResponseEntity.ok(ongService.updateOng(id, dto, logoFile));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        return ongService.deleteOng(id)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }
}
