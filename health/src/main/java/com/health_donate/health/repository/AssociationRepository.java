package com.health_donate.health.repository;

import com.health_donate.health.entity.Association;
import com.health_donate.health.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AssociationRepository extends JpaRepository<Association, Long> {
}
