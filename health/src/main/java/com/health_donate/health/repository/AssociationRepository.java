package com.health_donate.health.repository;

import com.health_donate.health.entity.Association;
import com.health_donate.health.entity.User;
import lombok.Getter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
@Repository
public interface AssociationRepository extends JpaRepository<Association, Long> {
    List<Association> findByUserId(Long userId);

    List<Association> findByStatut(String enAttente);
}
