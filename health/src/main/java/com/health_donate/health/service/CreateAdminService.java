package com.health_donate.health.service;


import com.health_donate.health.entity.Role;
import com.health_donate.health.entity.User;
import com.health_donate.health.enumT.UserRole;
import com.health_donate.health.repository.RoleRepository;
import com.health_donate.health.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;


@Service
@AllArgsConstructor
@Slf4j
public class CreateAdminService implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepo;



    @Override
    public void run(String... args) throws Exception {
        if (userRepository.findByEmail("admin@admin.com").isEmpty()) {
            User admin = new User();
            admin.setName("admin");
            admin.setEmail("admin@admin.com");
            admin.setActif(true);
            admin.setPassword(passwordEncoder.encode("admin123"));

            if (roleRepo.count() == 0) {
                Arrays.stream(UserRole.values()).forEach(type -> {
                    Role role = new Role();
                    role.setName(type);
                    roleRepo.save(role);
                });
            }

            Role role = roleRepo.findByName(UserRole.ADMIN_ROLE)
                    .orElseThrow(() -> new RuntimeException("Role ADMIN introuvable !"));
            admin.setRole(role);

            userRepository.save(admin);

            log.info(" Admin créé avec succès ");
        } else {
            log.info(" Ooops :)  Admin existe déjà Merci ! ");

        }


    }




}



