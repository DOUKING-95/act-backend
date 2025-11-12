package com.health_donate.health.repository;

import com.health_donate.health.entity.Actor;
import com.health_donate.health.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface ActorRepository  extends JpaRepository<Actor, Long> {
    Optional<Actor> findByEmail(String email);
    Optional<Actor> findByPhoneNumber(String phone);
}
