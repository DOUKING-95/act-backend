package com.health_donate.health.service;



import com.health_donate.health.entity.User;
import com.health_donate.health.entity.Validation;
import com.health_donate.health.repository.ValidationRepo;
import com.health_donate.health.service.external.EmailSender;
import com.parrainer_plus.p_plus.entitie.User;
import com.parrainer_plus.p_plus.entitie.Validation;
import com.parrainer_plus.p_plus.repository.ValidationRepo;
import com.parrainer_plus.p_plus.service.external.EmailSender;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;

import java.util.Random;

import static java.time.temporal.ChronoUnit.MINUTES;


@Service
@AllArgsConstructor
public class ValidationService {
private ValidationRepo validationRepo;
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
}

