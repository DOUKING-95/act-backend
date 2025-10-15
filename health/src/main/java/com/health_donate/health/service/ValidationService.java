package com.health_donate.health.service;



import com.health_donate.health.entity.User;
import com.health_donate.health.entity.Validation;
import com.health_donate.health.repository.UserRepository;
import com.health_donate.health.repository.ValidationRepo;
import com.health_donate.health.service.external.EmailSender;


import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.Instant;

import java.util.Map;
import java.util.Random;

import static java.time.temporal.ChronoUnit.MINUTES;


@Service
@AllArgsConstructor
public class ValidationService {
private ValidationRepo validationRepo;
private UserRepository userRepository;
    private EmailSender mailSender;


    public  void saveValidation(User user){
        Validation validation = new Validation();

        validation.setUser(user);
        Instant creation = Instant.now();
        Instant expiration = creation.plus(10, MINUTES);

        Random  random = new Random();
        int randomValue = random.nextInt(999999);

        String code = String.format("%06d", randomValue);

        validation.setCreation(creation);
        validation.setExpiration(expiration);
        validation.setCode(code);

        this.validationRepo.save(validation);
        String subject = "Code d'activation de Parrainage Plus";
        String message = String.format("Votre code d'activation est %s , Mr %s", code,user.getEmail());
        this.mailSender.sendSimpleEmail(user.getEmail(), subject, message);

    }


    public  String validationUtilsateur(Map<String, String> code){

        Validation validation =  this.validationRepo.findByCode(code.get("code"))
                .orElseThrow(()-> new UsernameNotFoundException("Pas de validation pour ce code"));

        User user = validation.getUser();
        user.setActif(true);
        this.userRepository.save(user);

        return "Validation recu";

    }
}

