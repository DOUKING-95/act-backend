package com.health_donate.health.repository;

import com.health_donate.health.entity.Association;
import com.health_donate.health.entity.DemandeAdhesion;
import com.health_donate.health.entity.User;
import com.health_donate.health.enumT.StatutDemande;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DemandeAdhesionRepository extends JpaRepository<DemandeAdhesion,Long> {


    List<DemandeAdhesion> findByAssociationAndStatut(Association association, StatutDemande statut);

    Optional<DemandeAdhesion> findByUserAndAssociation(User user, Association association);

    List<DemandeAdhesion> findByUser(User user);
}
