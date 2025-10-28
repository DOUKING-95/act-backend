package com.health_donate.health.repository;

import com.health_donate.health.entity.SocialAction;
import com.health_donate.health.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface SocialActionRepository extends JpaRepository<SocialAction, Long> {
    Page<SocialAction> findAll(Pageable pageable);
    Page<SocialAction> findByAssociationId(Long associationId, Pageable pageable);
}
