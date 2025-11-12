package com.health_donate.health.mapper;



import com.health_donate.health.dto.AuditLogDTO;
import com.health_donate.health.entity.AuditLog;
import com.health_donate.health.entity.User;

public class AuditLogMapper {

    // Convertir une entité vers un DTO
    public static AuditLogDTO toDTO(AuditLog log) {
        if (log == null) {
            return null;
        }

        AuditLogDTO dto = new AuditLogDTO();
        dto.setId(log.getId());
        dto.setAction(log.getAction());
        dto.setTimestamp(log.getTimestamp());
        dto.setUserId(log.getUser() != null ? log.getUser().getId() : null);
        return dto;
    }

    // Convertir un DTO vers une entité
    public static AuditLog toEntity(AuditLogDTO dto) {
        if (dto == null) {
            return null;
        }

        AuditLog log = new AuditLog();
        log.setId(dto.getId());
        log.setAction(dto.getAction());
        log.setTimestamp(dto.getTimestamp());

        if (dto.getUserId() != null) {
            User user = new User();
            user.setId(dto.getUserId());
            log.setUser(user);
        }

        return log;
    }
}

