package com.health_donate.health.repository;

import com.health_donate.health.entity.Ong;
import com.health_donate.health.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OngRepository extends JpaRepository<Ong, Long> {
}
