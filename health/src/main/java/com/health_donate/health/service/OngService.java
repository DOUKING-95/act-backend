package com.health_donate.health.service;



import com.health_donate.health.dto.OngDTO;
import com.health_donate.health.entity.Ong;
import com.health_donate.health.mapper.OngMapper;
import com.health_donate.health.repository.OngRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Service
@AllArgsConstructor
public class OngService {


    private OngRepository ongRepository;
    private final FileStorageService fileStorageService;

    // CREATE
    public OngDTO createOng(OngDTO dto, MultipartFile logo) throws IOException {
        Ong ong = OngMapper.toEntity(dto);


        if (logo != null && !logo.isEmpty()) {
            String logoPath = fileStorageService.storeFile(logo);
            ong.setLogoUrl(logoPath);
        }

        Ong saved = ongRepository.save(ong);
        return OngMapper.toDTO(saved);
    }

    // READ
    public OngDTO getOngById(Long id) {
        Optional<Ong> ongOpt = ongRepository.findById(id);
        return ongOpt.map(OngMapper::toDTO).orElse(null);
    }

    // UPDATE
    public OngDTO updateOng(Long id, OngDTO dto, MultipartFile logo) throws IOException {

        Ong ong = ongRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Ong introuvable pour l'id " + id));


        ong.setName(dto.getName());
        ong.setAddress(dto.getAddress());
        ong.setPhone(dto.getPhone());
        ong.setEmail(dto.getEmail());
        ong.setActive(dto.isActive());
        ong.setDescription(dto.getDescription());


        if (logo != null && !logo.isEmpty()) {
            String logoPath = fileStorageService.storeFile(logo);
            ong.setLogoUrl(logoPath);
        }


        Ong updated = ongRepository.save(ong);

        return OngMapper.toDTO(updated);
    }


    // DELETE
    public boolean deleteOng(Long id) {
        if (!ongRepository.existsById(id)) return false;
        ongRepository.deleteById(id);
        return true;
    }
}

