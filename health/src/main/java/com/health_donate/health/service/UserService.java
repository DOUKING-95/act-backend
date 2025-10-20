package com.health_donate.health.service;

import com.health_donate.health.dto.ActorDTO;
import com.health_donate.health.entity.Actor;
import com.health_donate.health.entity.User;
import com.health_donate.health.mapper.ActorMapper;
import com.health_donate.health.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService implements UserDetailsService {

    private UserRepository userRepository;

    public User save(User user) {
        return userRepository.save(user);
    }

    public Optional<User> getById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public UserDetails loadUserByUsername(String phoneNumber) throws UsernameNotFoundException {
        User user = userRepository.findByPhoneNumber(phoneNumber)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + phoneNumber));
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), user.getAuthorities());

    }

    public ActorDTO getUserByPhone(String phone) {

        Actor actor = (Actor) this.userRepository.findByPhoneNumber(phone).orElseThrow(()-> new EntityNotFoundException("Pas de user trouver avec ce numero"));

        return ActorMapper.toDTO(actor);
    }
}