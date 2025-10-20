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
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OngService {

    private final OngRepository ongRepository;
    private final FileStorageService fileStorageService;

    public OngService(OngRepository ongRepository, FileStorageService fileStorageService) {
        this.ongRepository = ongRepository;
        this.fileStorageService = fileStorageService;
    }

    public List<OngDTO> getAllOngs() {
        return ongRepository.findAll()
                .stream()
                .map(OngMapper::toDTO)
                .collect(Collectors.toList());
    }

    public OngDTO getById(Long id) {
        Optional<Ong> ongOpt = ongRepository.findById(id);
        return ongOpt.map(OngMapper::toDTO).orElse(null);
    }

    public OngDTO createOng(OngDTO dto, MultipartFile logoFile) throws IOException {
        Ong ong = OngMapper.toEntity(dto);

        if (logoFile != null && !logoFile.isEmpty()) {
            String logoPath = fileStorageService.storeFile(logoFile);
            ong.setLogoUrl(logoPath);
        }

        Ong saved = ongRepository.save(ong);
        return OngMapper.toDTO(saved);
    }

    public OngDTO updateOng(Long id, OngDTO dto, MultipartFile logoFile) throws IOException {
        Ong ong = ongRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ong introuvable avec id : " + id));

        // mise à jour des champs
        ong.setNom(dto.getNom());
        ong.setTypeOrganisation(dto.getTypeOrganisation());
        ong.setDescriptionMission(dto.getDescriptionMission());
        ong.setEmailContact(dto.getEmailContact());
        ong.setTelephoneContact(dto.getTelephoneContact());
        ong.setSiteWeb(dto.getSiteWeb());
        ong.setAdresse(dto.getAdresse());
        ong.setVille(dto.getVille());
        ong.setCodePostal(dto.getCodePostal());
        ong.setPays(dto.getPays());
        ong.setNomCompletRepresentant(dto.getNomCompletRepresentant());
        ong.setFonctionRepresentant(dto.getFonctionRepresentant());
        ong.setEmailRepresentant(dto.getEmailRepresentant());
        ong.setTelephoneRepresentant(dto.getTelephoneRepresentant());
        ong.setNumeroEnregistrement(dto.getNumeroEnregistrement());
        ong.setDateCreation(dto.getDateCreation());
        ong.setStatut(dto.getStatut());
        ong.setConfirmationOfficielle(dto.isConfirmationOfficielle());
        ong.setEstActif(dto.isEstActif());

        if (logoFile != null && !logoFile.isEmpty()) {
            String logoPath = fileStorageService.storeFile(logoFile);
            ong.setLogoUrl(logoPath);
        }

        Ong updated = ongRepository.save(ong);
        return OngMapper.toDTO(updated);
    }

    public boolean deleteOng(Long id) {
        if (!ongRepository.existsById(id)) return false;
        ongRepository.deleteById(id);
        return true;
    }
}

