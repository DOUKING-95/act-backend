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
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AssociationService {

    private final AssociationRepository associationRepository;
    private final FileStorageService fileStorageService;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    /**
     * Crée une nouvelle association et son représentant utilisateur.
     */
    public AssociationDTO createAssociation(AssociationDTO dto, MultipartFile logo, MultipartFile cover) throws IOException {
        // Vérification si l’utilisateur existe
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

        //  Associer le rôle "ASSOCIATION"
        Role roleAssociation = roleRepository.findByName(UserRole.ASSOCIATION_ROLE)
                .orElseThrow(() -> new RuntimeException("Le rôle 'ASSOCIATION_ROLE' n'existe pas"));
        representant.setRole(roleAssociation);
        userRepository.save(representant);


        Association association = AssociationMapper.toEntity(dto);
        association.setUser(representant);


        if (logo != null && !logo.isEmpty()) {
            association.setLogoUrl(fileStorageService.storeFile(logo));
        }
        if (cover != null && !cover.isEmpty()) {
            association.setCovertUrl(fileStorageService.storeFile(cover));
        }

        Association saved = associationRepository.save(association);
        return AssociationMapper.toDTO(saved);
    }

    /**
     * Retourne toutes les associations.
     */
    public List<AssociationDTO> allAssociations() {
        return associationRepository.findAll()
                .stream()
                .map(AssociationMapper::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Récupère une association par ID.
     */
    public AssociationDTO getAssociationById(Long id) {
        return associationRepository.findById(id)
                .map(AssociationMapper::toDTO)
                .orElseThrow(() -> new EntityNotFoundException("Association non trouvée avec l'id : " + id));
    }

    /**
     * Récupère les associations liées à un utilisateur.
     */
    public List<AssociationDTO> getAssociationsByUserId(Long userId) {
        return associationRepository.findByUser_Id(userId)
                .stream()
                .map(AssociationMapper::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Met à jour les informations d’une association.
     */
    public AssociationDTO updateAssociation(Long id, AssociationDTO dto, MultipartFile logo, MultipartFile cover) throws IOException {
        Association association = associationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Association introuvable pour l'id " + id));

        //  Mise à jour des champs
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

        //  Fichiers
        if (logo != null && !logo.isEmpty()) {
            association.setLogoUrl(fileStorageService.storeFile(logo));
        }
        if (cover != null && !cover.isEmpty()) {
            association.setCovertUrl(fileStorageService.storeFile(cover));
        }

        Association updated = associationRepository.save(association);
        return AssociationMapper.toDTO(updated);
    }

    /**
     * Modifie le statut d’une association.
     */
    public AssociationDTO updateStatut(Long id, String statut) {
        Association association = associationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Association introuvable pour l'id " + id));

        String statutStr = statut.replace("\"", "");
        StatutAsso statutEnum = StatutAsso.valueOf(statutStr);
        association.setStatut(statutEnum);
        association.setActive(statutEnum != StatutAsso.Desactif);

        Association saved = associationRepository.save(association);
        return AssociationMapper.toDTO(saved);
    }

    /**
     * Supprime une association.
     */
    public boolean deleteAssociation(Long id) {
        if (!associationRepository.existsById(id)) return false;
        associationRepository.deleteById(id);
        return true;
    }
}
