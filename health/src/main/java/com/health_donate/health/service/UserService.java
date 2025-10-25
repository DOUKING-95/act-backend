package com.health_donate.health.service;

import com.health_donate.health.dto.ActorDTO;
import com.health_donate.health.dto.UserDTO;
import com.health_donate.health.entity.Actor;
import com.health_donate.health.entity.Role;
import com.health_donate.health.entity.User;
import com.health_donate.health.mapper.ActorMapper;
import com.health_donate.health.mapper.DonationRequestMapper;
import com.health_donate.health.mapper.UserMapper;
import com.health_donate.health.repository.ActorRepository;
import com.health_donate.health.repository.RoleRepository;
import com.health_donate.health.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;


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



    //Pour les details de CRUD de Users


    public List<UserDTO> getAllUsers() {
        return userRepository.findAll().stream()
                .map(UserMapper::toDTO)
                .toList();
    }

    public UserDTO getUserById(Long id) {
        return userRepository.findById(id)
                .map(UserMapper::toDTO)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));
    }

    public UserDTO createUser(UserDTO dto) {
        Role role = roleRepository.findById(dto.getRoleId())
                .orElseThrow(() -> new RuntimeException("Rôle non trouvé"));

        User user = UserMapper.toEntity(dto, role);
        return UserMapper.toDTO(userRepository.save(user));
    }
}