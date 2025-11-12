package com.health_donate.health.controller;


import com.health_donate.health.dto.ApiResponse;
import com.health_donate.health.dto.ImageDTO;
import com.health_donate.health.service.ImageService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("images")
@AllArgsConstructor
public class ImageController {

    private ImageService imageService;



    //  CREATE
    @PostMapping("/create")
    public ResponseEntity<ApiResponse<ImageDTO>> createImage(@RequestBody ImageDTO dto) {
        ImageDTO saved = imageService.createImage(dto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>("201", "Image créée avec succès", saved));
    }

    //  Get BY ID
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ImageDTO>> getImageById(@PathVariable Long id) {
        ImageDTO dto = imageService.getImageById(id);
        if (dto == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>("404", "Image non trouvée", null));
        }
        return ResponseEntity.ok(new ApiResponse<>("200", "Image trouvée", dto));
    }

    //  READ ALL
    @GetMapping("/all")
    public ResponseEntity<ApiResponse<List<ImageDTO>>> getAllImages() {
        List<ImageDTO> images = imageService.getAllImages();
        return ResponseEntity.ok(new ApiResponse<>("200", "Liste des images", images));
    }

    //  UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ImageDTO>> updateImage(@PathVariable Long id, @RequestBody ImageDTO dto) {
        ImageDTO updated = imageService.updateImage(id, dto);
        if (updated == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>("404", "Image à mettre à jour non trouvée", null));
        }
        return ResponseEntity.ok(new ApiResponse<>("200", "Image mise à jour avec succès", updated));
    }

    //  DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Boolean>> deleteImage(@PathVariable Long id) {
        boolean deleted = imageService.deleteImage(id);
        if (!deleted) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>("404", "Image non trouvée", false));
        }
        return ResponseEntity.ok(new ApiResponse<>("200", "Image supprimée avec succès", true));
    }
}
