package com.health_donate.health.repository;

import com.health_donate.health.entity.Actor;
import com.health_donate.health.entity.Participation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ParticipationRepository  extends JpaRepository<Participation, Long> {
    Optional<Participation> findByActorIdAndActiviteId(Long actorId, Long activiteId);
    long countByActorIdAndStatus(Long actorId, boolean status);

    @Query("SELECT p.actor.id AS actorId, COUNT(p) AS totalParticipations " +
            "FROM Participation p " +
            "WHERE p.status = true " +
            "GROUP BY p.actor.id " +
            "ORDER BY COUNT(p) DESC")
    List<Object[]> findTopActorsByParticipation();

    long countByActiviteIdAndStatus(Long activiteId, boolean status);

    boolean existsByActorIdAndActiviteId(Long actorId, Long activiteId);




}