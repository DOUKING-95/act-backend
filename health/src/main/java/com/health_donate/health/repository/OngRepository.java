package com.health_donate.health.repository;

import com.health_donate.health.entity.Ong;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface OngRepository extends JpaRepository<Ong, Long> {
}
