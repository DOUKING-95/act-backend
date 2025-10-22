package com.health_donate.health.service;



import com.health_donate.health.dto.AssociationDTO;
import com.health_donate.health.dto.MembreDTO;
import com.health_donate.health.entity.Association;
import com.health_donate.health.entity.Membre;
import com.health_donate.health.entity.Role;
import com.health_donate.health.entity.User;
import com.health_donate.health.enumT.StatutAsso;
import com.health_donate.health.enumT.UserRole;
import com.health_donate.health.mapper.AssociationMapper;
import com.health_donate.health.mapper.MembreMapper;
import com.health_donate.health.repository.AssociationRepository;
import com.health_donate.health.repository.MembreRepository;
import com.health_donate.health.repository.RoleRepository;
import com.health_donate.health.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AssociationService {


    @Autowired
    private AssociationRepository associationRepository;
    @Autowired
    private final FileStorageService fileStorageService;
    @Autowired
    private final PasswordEncoder passwordEncoder;
    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private final RoleRepository roleRepository;
    @Autowired
    private MembreRepository membreRepository;

    //Creation d'une association
    /**
     * Crée une nouvelle association et son représentant utilisateur.
     */
    public AssociationDTO createAssociation(AssociationDTO dto, MultipartFile logo, MultipartFile cover) throws IOException {
        //Vérification si le représentant existe
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

        //Attribution du rôle "ASSOCIATION_ROLE"
        Role roleAssociation = roleRepository.findByName(UserRole.ASSOCIATION_ROLE)
                .orElseThrow(() -> new RuntimeException("Le rôle Association n'existe pas"));
        representant.setRole(roleAssociation);

        // Sauvegarde / mise à jour
        userRepository.save(representant);

        //Création de l'association
        Association association = AssociationMapper.toEntity(dto);
        association.setDateCreation(new Date());
        association.setStatut(StatutAsso.En_attente);
        association.setActive(true);
        association.setUser(representant);


        if (logo != null && !logo.isEmpty()) {
            String logoPath = fileStorageService.storeFile(logo);
            association.setLogoUrl(logoPath);
        }

        if (cover != null && !cover.isEmpty()) {
            String coverPath = fileStorageService.storeFile(cover);
            association.setCovertUrl(coverPath);
        }

        Association savedAssociation = associationRepository.save(association);

        //Création du premier membre (le représentant)
        Membre membre = new Membre();
        membre.setUser(representant);
        membre.setAssociation(savedAssociation);
        membre.setActif(true);
        membreRepository.save(membre);

        return AssociationMapper.toDTO(savedAssociation);
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
        Optional<Association> associationOpt = associationRepository.findById(id);
        return associationOpt.map(AssociationMapper::toDTO).orElse(null);
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

    /**
     * Modifie le statut d’une association.
     */
    public AssociationDTO updateStatut(Long id, String statut) {
        Association association = associationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Association introuvable pour l'id " + id));

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

    /**
     * Supprime une association.
     */
    public boolean deleteAssociation(Long id) {
        if (!associationRepository.existsById(id)) return false;
        associationRepository.deleteById(id);
        return true;
    }


    //Pour l'ajout de membre dans l'association
    public MembreDTO addMemberToAssociation(Long associationId, String emailOrPhone) {
        Association association = associationRepository.findById(associationId)
                .orElseThrow(() -> new EntityNotFoundException("Association introuvable pour l'id " + associationId));

        // Vérifier si le user existe
        User user = userRepository.findByEmailOrPhoneNumber(emailOrPhone, emailOrPhone)
                .orElseThrow(() -> new EntityNotFoundException("Utilisateur introuvable avec cet email/téléphone"));

        // Vérifier s’il n’est pas déjà membre
        boolean alreadyMember = membreRepository.existsByAssociationAndUser(association, user);
        if (alreadyMember) {
            throw new RuntimeException("Cet utilisateur est déjà membre de l'association");
        }

        // Ajouter comme membre
        Membre membre = new Membre();
        membre.setUser(user);
        membre.setAssociation(association);
        membre.setActif(true);
        Membre saved = membreRepository.save(membre);

        return MembreMapper.toDTO(saved);
    }

    //Pour la recuperation des membres d'une association
    public List<MembreDTO> getMembresByAssociation(Long associationId) {
        List<Membre> membres = membreRepository.findByAssociationId(associationId);
        return membres.stream()
                .map(MembreMapper::toDTO)
                .collect(Collectors.toList());
    }


}

