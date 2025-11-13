package com.health_donate.health.repository;

import com.health_donate.health.entity.Actor;
import com.health_donate.health.entity.Association;
import com.health_donate.health.entity.Membre;
import com.health_donate.health.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MembreRepository   extends JpaRepository<Membre, Long> {
    List<Membre> findByAssociationId(Long associationId);
    boolean existsByAssociationAndUser(Association association, User user);
}
