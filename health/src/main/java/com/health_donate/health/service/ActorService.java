package com.health_donate.health.service;



import com.health_donate.health.dto.ActorDTO;
import com.health_donate.health.dto.RegisterDTO;
import com.health_donate.health.entity.Actor;
import com.health_donate.health.entity.Role;
import com.health_donate.health.entity.User;
import com.health_donate.health.mapper.ActorMapper;
import com.health_donate.health.repository.ActorRepository;
import com.health_donate.health.repository.RoleRepository;
import com.health_donate.health.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;


@Service
@AllArgsConstructor
@Slf4j
public class ActorService {


    private ActorRepository actorRepository;
    private UserRepository userRepository;
    private BCryptPasswordEncoder passwordEncoder;
    private  ValidationService validationService;
    private RoleRepository roleRepository;


    // --- CREATE ---
    public ActorDTO createActor(RegisterDTO dto)  {

        Role role =  this.roleRepository.findById(1L).orElseThrow(()-> new EntityNotFoundException("Pas de role pour id :) 1"));
        log.info ("Recherche utilisateur avec phone: " + dto.phone());
        System.out.println("Email avant save : " + dto.phone());
       Optional<User> user = this.userRepository.findByPhoneNumber(dto.phone());

        log.info("Recherche utilisateur avec phone: " + dto.phone());
        System.out.println("Email DTO: " + dto.email());
        System.out.println("Phone DTO: " + dto.phone());


        if (user.isPresent()){
           throw  new RuntimeException("Ce numéro est déjà réconnu par le systeme Act ! Merci de renseignez un autre numéro .");
       }


        Actor actor = RegisterDTO.toEntity(dto);

        if (!Objects.equals(actor.getPassword(), dto.confirmPassword())){
            throw  new IllegalArgumentException("Veillez confirmer le mot de pass");

        }



        actor.setPassword(passwordEncoder.encode(dto.password()));
        actor.setRole(role);
        System.out.println("Email avant save : " + actor.getEmail());
        System.out.println("Phone avant save : " + actor.getPhoneNumber());


        Actor savedActor = actorRepository.save(actor);

        this.validationService.saveValidation(actor);
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

