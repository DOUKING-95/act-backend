package com.health_donate.health.controller;



import com.health_donate.health.dto.ActorDTO;
import com.health_donate.health.dto.ApiResponse;
import com.health_donate.health.dto.RegisterDTO;
import com.health_donate.health.service.ActorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RestController
@RequestMapping("actors")
@RequiredArgsConstructor
@Slf4j
public class ActorController {

    private final ActorService actorService;

    @GetMapping("/uploads-test/{filename}")
    public ResponseEntity<Resource> testFile(@PathVariable String filename) throws MalformedURLException {
        Path path = Paths.get("/Users/MacBookAir/Documents/health-donate-backend/health/uploads/").resolve(filename);
        Resource resource = new UrlResource(path.toUri());
        if (!resource.exists()) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(resource);
    }


    @PostMapping("/register")
    public ResponseEntity<ApiResponse<?>> createActor(@RequestBody RegisterDTO dto) {
        try {
            ActorDTO created = actorService.createActor(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(
                    new ApiResponse<>(
                            String.valueOf(HttpStatus.CREATED.value()),
                            "Acteur créé avec succès",
                            created
                    )
            );
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ApiResponse<>(
                            String.valueOf(HttpStatus.BAD_REQUEST.value()),
                            e.getMessage(),
                            null
                    )
            );
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(
                    new ApiResponse<>(
                            String.valueOf(HttpStatus.CONFLICT.value()),
                            e.getMessage(),
                            null
                    )
            );
        } catch (Exception e) {
            log.error("Erreur lors de la création d’un acteur", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new ApiResponse<>(
                            String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()),
                            "Erreur interne du serveur",
                            null
                    )
            );
        }
    }


    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<?>> getActorById(@PathVariable Long id) {
        ActorDTO actor = actorService.getActorById(id);
        if (actor == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ApiResponse<>(
                            String.valueOf(HttpStatus.NOT_FOUND.value()),
                            "Acteur non trouvé",
                            null
                    )
            );
        }
        return ResponseEntity.ok(
                new ApiResponse<>(
                        String.valueOf(HttpStatus.OK.value()),
                        "Acteur trouvé",
                        actor
                )
        );
    }


    @PostMapping("/{id}")
    public ResponseEntity<ApiResponse<?>> updateActor(@PathVariable Long id, @RequestBody ActorDTO dto) {
        ActorDTO updated = actorService.updateActor(id, dto);
        if (updated == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ApiResponse<>(
                            String.valueOf(HttpStatus.NOT_FOUND.value()),
                            "Acteur non trouvé pour mise à jour",
                            null
                    )
            );
        }
        return ResponseEntity.ok(
                new ApiResponse<>(
                        String.valueOf(HttpStatus.OK.value()),
                        "Acteur mis à jour avec succès",
                        updated
                )
        );
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<?>> deleteActor(@PathVariable Long id) {
        boolean deleted = actorService.deleteActor(id);
        if (!deleted) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ApiResponse<>(
                            String.valueOf(HttpStatus.NOT_FOUND.value()),
                            "Acteur non trouvé pour suppression",
                            null
                    )
            );
        }
        return ResponseEntity.ok(
                new ApiResponse<>(
                        String.valueOf(HttpStatus.OK.value()),
                        "Acteur supprimé avec succès",
                        null
                )
        );
    }
}

