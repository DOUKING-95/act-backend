package com.health_donate.health.service;



import com.health_donate.health.dto.SocialActionDTO;
import com.health_donate.health.entity.Association;
import com.health_donate.health.entity.Image;
import com.health_donate.health.entity.SocialAction;
import com.health_donate.health.mapper.SocialActionMapper;
import com.health_donate.health.repository.AssociationRepository;
import com.health_donate.health.repository.ImageRepository;
import com.health_donate.health.repository.SocialActionRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class SocialActionService {


    private SocialActionRepository socialActionRepository;
    private ImageRepository imageRepository;
    private AssociationRepository associationRepository;
    private  FileStorageService fileStorageService;

    // CREATE
    public SocialActionDTO createSocialAction(SocialActionDTO dto, MultipartFile[] images) throws IOException {

        Association assoc = associationRepository.findById(dto.getAssociationId())
                .orElseThrow(() -> new RuntimeException("Association introuvable"));

        SocialAction action = SocialActionMapper.toEntity(dto);
        action.setAssociation(assoc);


        SocialAction savedAction = socialActionRepository.save(action);


        List<Image> savedImages = new ArrayList<>();
        if (images != null) {
            for (MultipartFile file : images) {
                String path = fileStorageService.storeFile(file);

                Image img = new Image();
                img.setName(file.getOriginalFilename());
                img.setUrl(path);
                img.setSocialAction(savedAction);
                savedImages.add(imageRepository.save(img));
            }
        }

        action.getImages().clear();
        action.getImages().addAll(savedImages);

        socialActionRepository.save(savedAction);

        return SocialActionMapper.toDTO(savedAction);
    }

 // GET BY ID
    public SocialActionDTO getSocialActionById(Long id) {
        Optional<SocialAction> opt = socialActionRepository.findById(id);
        return opt.map(SocialActionMapper::toDTO).orElse(null);
    }

    public Page<SocialActionDTO> getAllSocialActionsPaged(int page) {
        int size = 5;
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "id"));

        Page<SocialAction> actionsPage = socialActionRepository.findAll(pageRequest);


        return actionsPage.map(SocialActionMapper::toDTO);
    }

    public Page<SocialActionDTO> getActivitiesByAssociation(Long associationId, int page ) {
        int size = 5;
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "id"));
        Page<SocialAction> actionsPage = socialActionRepository.findByAssociationId(associationId, pageRequest);


        return actionsPage.map(SocialActionMapper::toDTO);
    }

//GET ALL
    public List<SocialActionDTO> getAllSocialActions() {
        List<SocialAction> actions = socialActionRepository.findAll();
        return actions.stream()
                .map(SocialActionMapper::toDTO)
                .collect(Collectors.toList());
    }


    // UPDATE
    public SocialActionDTO updateSocialAction(Long id, SocialActionDTO dto, MultipartFile[] images) throws IOException {

        SocialAction action = socialActionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("SocialAction introuvable pour l'id " + id));


        action.setTitre(dto.getTitre());
        action.setLieu(dto.getLieu());
        action.setDescription(dto.getDescription());
        action.setPassed(dto.isPassed());
        action.setBenevolNumber(dto.getBenevolNumber());


        SocialAction updatedAction = socialActionRepository.save(action);


        if (images != null && images.length > 0) {

            List<Image> oldImages = imageRepository.findBySocialActionId(updatedAction.getId());
            imageRepository.deleteAll(oldImages);

            List<Image> savedImages = new ArrayList<>();
            for (MultipartFile file : images) {
                String path = fileStorageService.storeFile(file);

                Image img = new Image();
                img.setName(file.getOriginalFilename());
                img.setUrl(path);
                img.setSocialAction(updatedAction);
                savedImages.add(imageRepository.save(img));
            }
            updatedAction.setImages(savedImages);
        }


        updatedAction = socialActionRepository.save(updatedAction);

        return SocialActionMapper.toDTO(updatedAction);
    }


    // DELETE
    public boolean deleteSocialAction(Long id) {
        if (!socialActionRepository.existsById(id)) return false;
        socialActionRepository.deleteById(id);
        return true;
    }
}

