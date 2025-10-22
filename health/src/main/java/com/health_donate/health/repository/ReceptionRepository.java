package com.health_donate.health.repository;

import com.health_donate.health.entity.Reception;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReceptionRepository extends JpaRepository<Reception, Long> {
    List<Reception> findByNotificationId(Long notificationId);
    void deleteAllByNotificationId(Long notificationId);
}
