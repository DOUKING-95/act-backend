package com.health_donate.health.service;



import com.health_donate.health.dto.AssociationDTO;
import com.health_donate.health.entity.Association;
import com.health_donate.health.entity.Role;
import com.health_donate.health.entity.User;
import com.health_donate.health.enumT.StatutAsso;
import com.health_donate.health.enumT.UserRole;
import com.health_donate.health.mapper.AssociationMapper;
import com.health_donate.health.repository.AssociationRepository;
import com.health_donate.health.repository.RoleRepository;
import com.health_donate.health.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AssociationService {


    private AssociationRepository associationRepository;
    private final FileStorageService fileStorageService;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public AssociationDTO createAssociation(AssociationDTO dto, MultipartFile logo, MultipartFile cover) throws IOException {
        // Création / mise à jour du représentant dans Users
        User representant = userRepository.findByEmailOrPhoneNumber(dto.getEmail(), dto.getPhone())
                .orElseGet(() -> {
                    User newUser = new User();
                    newUser.setEmail(dto.getEmail());
                    newUser.setName(dto.getNomComplet());
                    newUser.setPhoneNumber(dto.getPhone());
                    newUser.setPassword(passwordEncoder.encode("association123"));
                    newUser.setActif(true);
                    newUser.setVerified(true);
                    return newUser;
                });

        Role roleAssociation = roleRepository.findByName(UserRole.ASSOCIATION_ROLE)
                .orElseThrow(() -> new RuntimeException("Le rôle Association n'existe pas"));
        representant.setRole(roleAssociation);

        // Sauvegarde / mise à jour
        userRepository.save(representant);

        // 2️⃣ Création de l’association
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

    public List<AssociationDTO> allAssociation(){
       return associationRepository.findAll()
                .stream()
                .map(AssociationMapper::toDTO)
                .collect(Collectors.toList());
    }

    // READ
    public AssociationDTO getAssociationById(Long id) {
        Optional<Association> associationOpt = associationRepository.findById(id);
        return associationOpt.map(AssociationMapper::toDTO).orElse(null);
    }

    // UPDATE
    public AssociationDTO updateAssociation(Long id, AssociationDTO dto, MultipartFile logo, MultipartFile cover) throws IOException {

        Association association = associationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Association introuvable pour l'id " + id));

        association.setName(dto.getName());
        association.setAddress(dto.getAddress());
        association.setPhone(dto.getPhone());
        association.setEmail(dto.getEmail());
        association.setActive(dto.getActive());
        association.setDescription(dto.getDescription());

        association.setStatut(dto.getStatut());
        association.setDateCreation(dto.getDateCreation());
        association.setTypeAssociation(dto.getTypeAssociation());
        association.setSiteWeb(dto.getSiteWeb());
        association.setVille(dto.getVille());
        association.setCodePostal(dto.getCodePostal());
        association.setPays(dto.getPays());
        association.setNomComplet(dto.getNomComplet());
        association.setFonction(dto.getFonction());
        association.setNumeroEnregistrement(dto.getNumeroEnregistrement());
        association.setConfirmationOfficielle(dto.getConfirmationOfficielle());

        if (logo != null && !logo.isEmpty()) {
            String logoPath = fileStorageService.storeFile(logo);
            association.setLogoUrl(logoPath);
        }


        if (cover != null && !cover.isEmpty()) {
            String coverPath = fileStorageService.storeFile(cover);
            association.setCovertUrl(coverPath);
        }


        Association updated = associationRepository.save(association);

        return AssociationMapper.toDTO(updated);
    }


    //Modification du statut :
    public AssociationDTO updateStatut(Long id,String statut){
        Association association = associationRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Association introuvable pour l'id " + id));
        String statutStr = statut.replace("\"", "");
        StatutAsso statutEnum = StatutAsso.valueOf(statutStr);
        association.setStatut(statutEnum);
        if (statutEnum == StatutAsso.Desactif){
            association.setActive(false);
        }else {
            association.setActive(true);
        }
        Association asso = associationRepository.save(association);
        return  AssociationMapper.toDTO(asso);
    }

    // DELETE
    public boolean deleteAssociation(Long id) {
        if (!associationRepository.existsById(id)) return false;
        associationRepository.deleteById(id);
        return true;
    }
}

