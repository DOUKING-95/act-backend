package com.health_donate.health.service;

package com.health_donate.health.service;

import com.health_donate.health.dto.AssociationDTO;
import com.health_donate.health.entity.Association;
import com.health_donate.health.mapper.AssociationMapper;
import com.health_donate.health.repository.AssociationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AssociationService {

    @Autowired
    private AssociationRepository associationRepository;

    // CREATE
    public AssociationDTO createAssociation(AssociationDTO dto) {
        Association association = AssociationMapper.toEntity(dto);
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

