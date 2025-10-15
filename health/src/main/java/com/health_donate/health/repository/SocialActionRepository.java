package com.health_donate.health.repository;

import com.health_donate.health.entity.SocialAction;
import com.health_donate.health.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SocialActionRepository extends JpaRepository<SocialAction, Long> {
}
