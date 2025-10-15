package com.health_donate.health.repository;

import com.health_donate.health.entity.Association;
import com.health_donate.health.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AssociationRepository extends JpaRepository<Association, Long> {
}
