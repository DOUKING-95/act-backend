package com.health_donate.health.service;



import com.health_donate.health.dto.OngDTO;
import com.health_donate.health.entity.Ong;
import com.health_donate.health.mapper.OngMapper;
import com.health_donate.health.repository.OngRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class OngService {


    private OngRepository ongRepository;

    // CREATE
    public OngDTO createOng(OngDTO dto) {
        Ong ong = OngMapper.toEntity(dto);
        Ong saved = ongRepository.save(ong);
        return OngMapper.toDTO(saved);
    }

    // READ
    public OngDTO getOngById(Long id) {
        Optional<Ong> ongOpt = ongRepository.findById(id);
        return ongOpt.map(OngMapper::toDTO).orElse(null);
    }

    // UPDATE
    public OngDTO updateOng(Long id, OngDTO dto) {
        Optional<Ong> ongOpt = ongRepository.findById(id);
        if (ongOpt.isEmpty()) return null;

        Ong ong = ongOpt.get();
        ong.setName(dto.getName());
        ong.setAddress(dto.getAddress());
        ong.setPhone(dto.getPhone());
        ong.setEmail(dto.getEmail());
        ong.setLogoUrl(dto.getLogoUrl());
        ong.setActive(dto.isActive());
        ong.setDescription(dto.getDescription());

        Ong updated = ongRepository.save(ong);
        return OngMapper.toDTO(updated);
    }

    // DELETE
    public boolean deleteOng(Long id) {
        if (!ongRepository.existsById(id)) return false;
        ongRepository.deleteById(id);
        return true;
    }
}

