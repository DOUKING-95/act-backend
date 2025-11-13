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

String code = generateCode();
        validation.setCreation(creation);
        validation.setExpiration(expiration);
        validation.setCode(code);

        this.validationRepo.save(validation);
        String subject = "Code d'activation de Act !";
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





    public String resendValidation(String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Aucun utilisateur trouvé avec cet email"));


        Validation validation = validationRepo.findByUser(user)
                .orElseThrow(() -> new IllegalStateException("Aucune validation existante pour cet utilisateur"));

     //   if (validation.getExpiration().isAfter(Instant.now())) {
    //        return "Le code actuel n’a pas encore expiré. Veuillez patienter avant de redemander un nouveau code.";
    //    }


        String newCode = generateCode();
        validation.setCode(newCode);
        validation.setCreation(Instant.now());
        validation.setExpiration(Instant.now().plus(10, MINUTES));
        validationRepo.save(validation);


        sendValidationEmail(user, newCode);

        return " Un nouveau code de validation a été envoyé à " + email;
    }


    private String generateCode() {
        Random random = new Random();
        int randomValue = random.nextInt(999999);
        return String.format("%06d", randomValue);
    }


    private void sendValidationEmail(User user, String code) {
        String subject = " Nouveau code d'activation - Act";
        String message = String.format(
                "Bonjour %s,\n\nVotre nouveau code d’activation est : %s\n\nCe code expirera dans 10 minutes.\n\nMerci,\nL’équipe Act ",
                user.getName() != null ? user.getName() : user.getEmail(),
                code
        );
        mailSender.sendSimpleEmail(user.getEmail(), subject, message);
    }
}

