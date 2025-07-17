package com.health_donate.health.service;

import com.health_donate.health.entity.User;
import com.health_donate.health.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public User save(User user) {
        return userRepository.save(user);
    }

    public Optional<User> getById(Long id) {
        return userRepository.findById(id);
    }
}
