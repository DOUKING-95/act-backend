package com.health_donate.health.repository;

import com.health_donate.health.entity.Actor;
import com.health_donate.health.entity.Participation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ParticipationRepository  extends JpaRepository<Participation, Long> {
    Optional<Participation> findByActorIdAndActiviteId(Long actorId, Long activiteId);
    long countByActorIdAndStatus(Long actorId, boolean status);


}