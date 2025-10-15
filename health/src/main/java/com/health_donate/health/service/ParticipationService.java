package com.health_donate.health.service;


import com.health_donate.health.dto.ParticipationDTO;
import com.health_donate.health.entity.Actor;
import com.health_donate.health.entity.Participation;
import com.health_donate.health.entity.SocialAction;

import com.health_donate.health.mapper.ParticipationMapper;
import com.health_donate.health.repository.ActorRepository;
import com.health_donate.health.repository.ParticipationRepository;
import com.health_donate.health.repository.SocialActionRepository;
import com.health_donate.health.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;


@AllArgsConstructor
@Service
public class ParticipationService {


    private ParticipationRepository participationRepository;


    private SocialActionRepository socialActionRepository;


    private UserRepository userRepository;
    private ActorRepository actorRepository;


    // --- CREATE ---
    public ParticipationDTO createParticipation(ParticipationDTO dto) {

        Actor actor =  this.actorRepository.findById(dto.getActeurId()).orElseThrow(()-> new EntityNotFoundException("Pas de acteur pour cette" + dto.getActeurId()));
        SocialAction activite =  this.socialActionRepository.findById(dto.getActiviteId()).orElseThrow(()-> new EntityNotFoundException("Pas de acteur pour cette" + dto.getActeurId()));
        Participation participation = ParticipationMapper.toEntity(dto , actor, activite);

        if (dto.getActeurId() != null) {
            Optional<Actor> userOpt = actorRepository.findById(dto.getActeurId());
            userOpt.ifPresent(participation::setActeur);
        }

        if (dto.getActiviteId() != null) {
            Optional<SocialAction> actionOpt = socialActionRepository.findById(dto.getActiviteId());
            actionOpt.ifPresent(participation::setActivite);
        }

        Participation saved = participationRepository.save(participation);
        return ParticipationMapper.toDTO(saved);
    }

    // --- READ ---
    public ParticipationDTO getParticipationById(Long id) {
        Optional<Participation> opt = participationRepository.findById(id);
        return opt.map(ParticipationMapper::toDTO).orElse(null);
    }

    // --- UPDATE ---
    public ParticipationDTO updateParticipation(Long id, ParticipationDTO dto) {
        Optional<Participation> opt = participationRepository.findById(id);
        if (opt.isEmpty()) return null;

        Participation participation = opt.get();
        participation.setStatus(dto.getStatus());


        Participation updated = participationRepository.save(participation);
        return ParticipationMapper.toDTO(updated);
    }

    // --- DELETE ---
    public boolean deleteParticipation(Long id) {
        if (!participationRepository.existsById(id)) return false;
        participationRepository.deleteById(id);
        return true;
    }
}

