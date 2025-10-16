package com.health_donate.health.service;



import com.health_donate.health.dto.AssociationDTO;
import com.health_donate.health.entity.Association;
import com.health_donate.health.mapper.AssociationMapper;
import com.health_donate.health.repository.AssociationRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AssociationService {


    private AssociationRepository associationRepository;
    private final FileStorageService fileStorageService;

    // CREATE
    public AssociationDTO createAssociation(AssociationDTO dto, MultipartFile logo, MultipartFile cover) throws IOException, IOException {
        Association association = AssociationMapper.toEntity(dto);


        if (logo != null && !logo.isEmpty()) {
            String logoPath = fileStorageService.storeFile(logo);
            association.setLogoUrl(logoPath);
        }


        if (cover != null && !cover.isEmpty()) {
            String coverPath = fileStorageService.storeFile(cover);
            association.setCovertUrl(coverPath);
        }


        Association saved = associationRepository.save(association);

        return AssociationMapper.toDTO(saved);
    }

    // READ
    public AssociationDTO getAssociationById(Long id) {
        Optional<Association> associationOpt = associationRepository.findById(id);
        return associationOpt.map(AssociationMapper::toDTO).orElse(null);
    }

    // UPDATE
    public AssociationDTO updateAssociation(Long id, AssociationDTO dto) {
        Optional<Association> associationOpt = associationRepository.findById(id);
        if (associationOpt.isEmpty()) return null;

        Association association = associationOpt.get();
        association.setName(dto.getName());
        association.setAddress(dto.getAddress());
        association.setPhone(dto.getPhone());
        association.setEmail(dto.getEmail());
        association.setLogoUrl(dto.getLogoUrl());
        association.setActive(dto.isActive());
        association.setDescription(dto.getDescription());

        Association updated = associationRepository.save(association);
        return AssociationMapper.toDTO(updated);
    }

    // DELETE
    public boolean deleteAssociation(Long id) {
        if (!associationRepository.existsById(id)) return false;
        associationRepository.deleteById(id);
        return true;
    }
}

