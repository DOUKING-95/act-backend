package com.health_donate.health.service;

package com.health_donate.health.service;

import com.health_donate.health.dto.ActorDTO;
import com.health_donate.health.entity.Actor;
import com.health_donate.health.mapper.ActorMapper;
import com.health_donate.health.repository.ActorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ActorService {

    @Autowired
    private ActorRepository actorRepository;

    // --- CREATE ---
    public ActorDTO createActor(ActorDTO dto) {
        Actor actor = ActorMapper.toEntity(dto);
        Actor savedActor = actorRepository.save(actor);
        return ActorMapper.toDTO(savedActor);
    }

    // --- READ ---
    public ActorDTO getActorById(Long id) {
        Optional<Actor> actorOpt = actorRepository.findById(id);
        return actorOpt.map(ActorMapper::toDTO).orElse(null);
    }

    // --- UPDATE ---
    public ActorDTO updateActor(Long id, ActorDTO dto) {
        Optional<Actor> actorOpt = actorRepository.findById(id);
        if (actorOpt.isEmpty()) return null;

        Actor actor = actorOpt.get();
        actor.setName(dto.getName());
        actor.setFirstname(dto.getFirstname());
        actor.setEmail(dto.getEmail());
        actor.setPhoneNumber(dto.getPhoneNumber());
        actor.setActif(dto.isActif());
        actor.setVerified(dto.isVerified());

        // Ici, on peut gérer role si besoin
        Actor updatedActor = actorRepository.save(actor);
        return ActorMapper.toDTO(updatedActor);
    }

    // --- DELETE ---
    public boolean deleteActor(Long id) {
        if (!actorRepository.existsById(id)) return false;
        actorRepository.deleteById(id);
        return true;
    }
}

