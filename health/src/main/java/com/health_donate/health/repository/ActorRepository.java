package com.health_donate.health.repository;

import com.health_donate.health.entity.Actor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ActorRepository  extends JpaRepository<Actor, Long> {
}
