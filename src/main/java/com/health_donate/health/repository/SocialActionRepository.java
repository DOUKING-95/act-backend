package com.health_donate.health.repository;

import com.health_donate.health.entity.SocialAction;
import com.health_donate.health.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface SocialActionRepository extends JpaRepository<SocialAction, Long> {
    Page<SocialAction> findAll(Pageable pageable);
    Page<SocialAction> findByAssociationId(Long associationId, Pageable pageable);

    // Nombre d'actions par mois
    @Query("SELECT MONTHNAME(sa.date) as month, COUNT(sa.id) as count " +
            "FROM SocialAction sa " +
            "GROUP BY MONTHNAME(sa.date) " +
            "ORDER BY MIN(sa.date)")
    List<MonthCount> findActionsPerMonth();

    // Nombre d'actions par type
    @Query("SELECT sa.type as type, COUNT(sa.id) as count " +
            "FROM SocialAction sa " +
            "GROUP BY sa.type")
    List<TypeCount> findActionsPerType();

    // Dernières actions sociales
    @Query("SELECT sa FROM SocialAction sa " +
            "LEFT JOIN FETCH sa.association a " +
            "ORDER BY sa.date DESC")
    List<SocialAction> findLatestActions();

    // Interfaces projection pour Map conversion
    interface MonthCount {
        String getMonth();
        Long getCount();
    }

    interface TypeCount {
        String getType();
        Long getCount();
    }
}
