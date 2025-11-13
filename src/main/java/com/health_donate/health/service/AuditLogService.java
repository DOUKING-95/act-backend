package com.health_donate.health.service;


import com.health_donate.health.dto.AuditLogDTO;
import com.health_donate.health.entity.AuditLog;
import com.health_donate.health.mapper.AuditLogMapper;
import com.health_donate.health.repository.AuditLogRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class AuditLogService {


    private AuditLogRepository auditLogRepository;

    // CREATE
    public AuditLogDTO createAuditLog(AuditLogDTO dto) {
        AuditLog log = AuditLogMapper.toEntity(dto);
        AuditLog saved = auditLogRepository.save(log);
        return AuditLogMapper.toDTO(saved);
    }

    // READ
    public AuditLogDTO getAuditLogById(Long id) {
        Optional<AuditLog> opt = auditLogRepository.findById(id);
        return opt.map(AuditLogMapper::toDTO).orElse(null);
    }

    // UPDATE
    public AuditLogDTO updateAuditLog(Long id, AuditLogDTO dto) {
        Optional<AuditLog> opt = auditLogRepository.findById(id);
        if (opt.isEmpty()) return null;

        AuditLog log = opt.get();
        log.setAction(dto.getAction());

        AuditLog updated = auditLogRepository.save(log);
        return AuditLogMapper.toDTO(updated);
    }

    // DELETE
    public boolean deleteAuditLog(Long id) {
        if (!auditLogRepository.existsById(id)) return false;
        auditLogRepository.deleteById(id);
        return true;
    }
}

