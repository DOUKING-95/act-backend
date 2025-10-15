package com.health_donate.health.repository;

import com.health_donate.health.entity.Actor;
import com.health_donate.health.entity.AuditLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ActorRepository  extends JpaRepository<Actor, Long> {
}
