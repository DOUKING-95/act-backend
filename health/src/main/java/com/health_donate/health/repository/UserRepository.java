package com.health_donate.health.repository;

import com.health_donate.health.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    Optional<User> findByPhoneNumber(String phone);

    boolean existsByEmail(String email);
    boolean existsByPhoneNumber(String email);



}
