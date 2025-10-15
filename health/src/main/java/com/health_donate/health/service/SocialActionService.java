package com.health_donate.health.service;



import com.health_donate.health.dto.SocialActionDTO;
import com.health_donate.health.entity.SocialAction;
import com.health_donate.health.mapper.SocialActionMapper;
import com.health_donate.health.repository.SocialActionRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class SocialActionService {


    private SocialActionRepository socialActionRepository;

    // CREATE
    public SocialActionDTO createSocialAction(SocialActionDTO dto) {
        SocialAction action = SocialActionMapper.toEntity(dto);
        SocialAction saved = socialActionRepository.save(action);
        return SocialActionMapper.toDTO(saved);
    }

    // READ
    public SocialActionDTO getSocialActionById(Long id) {
        Optional<SocialAction> opt = socialActionRepository.findById(id);
        return opt.map(SocialActionMapper::toDTO).orElse(null);
    }

    // UPDATE
    public SocialActionDTO updateSocialAction(Long id, SocialActionDTO dto) {
        Optional<SocialAction> opt = socialActionRepository.findById(id);
        if (opt.isEmpty()) return null;

        SocialAction action = opt.get();
        action.setTitre(dto.getTitre());
        action.setLieu(dto.getLieu());
        action.setDescription(dto.getDescription());
        action.setPassed(dto.isPassed());
        action.setBenevolNumber(dto.getBenevolNumber());

        SocialAction updated = socialActionRepository.save(action);
        return SocialActionMapper.toDTO(updated);
    }

    // DELETE
    public boolean deleteSocialAction(Long id) {
        if (!socialActionRepository.existsById(id)) return false;
        socialActionRepository.deleteById(id);
        return true;
    }
}

