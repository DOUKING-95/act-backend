package com.health_donate.health.repository;

import com.health_donate.health.entity.Association;
import com.health_donate.health.enumT.StatutAsso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AssociationRepository extends JpaRepository<Association, Long> {
    List<Association> findByUserId(Long userId);

    List<Association> findByStatut(String enAttente);

    List<Association> findTop3ByStatut(StatutAsso statut);
}
