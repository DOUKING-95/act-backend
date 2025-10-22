package com.health_donate.health.repository;


import com.health_donate.health.entity.User;
import com.health_donate.health.entity.Validation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ValidationRepo  extends JpaRepository<Validation, Long> {
    Optional<Validation> findByCode(String code);

    Optional<Validation> findByUser(User user);
}
