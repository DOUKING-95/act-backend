package com.health_donate.health.service;


import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.health_donate.health.dto.ParticipationDTO;
import com.health_donate.health.dto.TopParticipantDTO;
import com.health_donate.health.entity.Actor;
import com.health_donate.health.entity.Participation;
import com.health_donate.health.entity.SocialAction;

import com.health_donate.health.mapper.ParticipationMapper;
import com.health_donate.health.repository.ActorRepository;
import com.health_donate.health.repository.ParticipationRepository;
import com.health_donate.health.repository.SocialActionRepository;
import com.health_donate.health.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.SecureRandom;
import java.time.Instant;
import java.util.*;


@AllArgsConstructor
@Service
public class ParticipationService {


    private ParticipationRepository participationRepository;
    private SocialActionRepository socialActionRepository;
    private ActorRepository actorRepository;


    // --- CREATE ---
    public ParticipationDTO createParticipation(ParticipationDTO dto) {

        Actor actor =  this.actorRepository.findById(dto.getActorId()).orElseThrow(()-> new EntityNotFoundException("Pas de acteur pour cette" + dto.getActorId()));
        SocialAction activite =  this.socialActionRepository.findById(dto.getActiviteId()).orElseThrow(()-> new EntityNotFoundException("Pas de acteur pour cette" + dto.getActorId()));
        Participation participation = ParticipationMapper.toEntity(dto , actor, activite);

        if (dto.getActorId() != null) {
            Optional<Actor> userOpt = actorRepository.findById(dto.getActorId());
            userOpt.ifPresent(participation::setActor);
        }

        if (dto.getActiviteId() != null) {
            Optional<SocialAction> actionOpt = socialActionRepository.findById(dto.getActiviteId());
            actionOpt.ifPresent(participation::setActivite);
        }

        Participation saved = participationRepository.save(participation);
        return ParticipationMapper.toDTO(saved);
    }

    // --- READ ALL ---
    public List<ParticipationDTO> getAllParticipations() {
        List<Participation> participations = participationRepository.findAll();
        return participations.stream()
                .map(ParticipationMapper::toDTO)
                .toList();
    }


    // --- READ ---
    public ParticipationDTO getParticipationById(Long id) {
        Optional<Participation> opt = participationRepository.findById(id);
        return opt.map(ParticipationMapper::toDTO).orElse(null);
    }

    // --- UPDATE ---
    public ParticipationDTO updateParticipation(Long id, ParticipationDTO dto) {
        Optional<Participation> opt = participationRepository.findById(id);
        if (opt.isEmpty()) return null;

        Participation participation = opt.get();
        participation.setStatus(dto.getSatus());


        Participation updated = participationRepository.save(participation);
        return ParticipationMapper.toDTO(updated);
    }

    // --- DELETE ---
    public boolean deleteParticipation(Long id) {
        if (!participationRepository.existsById(id)) return false;
        participationRepository.deleteById(id);
        return true;
    }


    public List<TopParticipantDTO> getTop15Participants() {
        List<Object[]> results = participationRepository.findTopActorsByParticipation();

        return results.stream()
                .map(obj -> new TopParticipantDTO(
                        ((Number) obj[0]).longValue(),
                        ((Number) obj[1]).longValue()))
                .limit(15)
                .toList();
    }

    // Vérifier si un user a déjà participé
    public boolean hasParticipated(Long actorId, Long activiteId) {
        return participationRepository.existsByActorIdAndActiviteId(actorId, activiteId);
    }

    // Compter les participations validées d'une activité
    public long countParticipations(Long activiteId) {
        return participationRepository.countByActiviteIdAndStatus(activiteId, false);
    }


    private final long CODE_VALIDITY_SECONDS = 300; // 5 minutes, configurable

    // Générer et associer un QR code à l'activité (retourne PNG bytes)
    public byte[] generateQRCodeForActivity(Long activiteId, boolean singleUse) throws WriterException, IOException {
        SocialAction action = socialActionRepository.findById(activiteId)
                .orElseThrow(() -> new RuntimeException("Activité introuvable."));

        String code = generateRandomCode(8);
        Instant expiration = Instant.now().plusSeconds(CODE_VALIDITY_SECONDS);

        action.setQrCode(code);
        action.setQrCodeExpiration(expiration);
        action.setQrCodeSingleUse(singleUse);
        socialActionRepository.save(action);

        String qrContent = "PRESENCE:" + code;
        QRCodeWriter writer = new QRCodeWriter();
        BitMatrix bitMatrix = writer.encode(qrContent, BarcodeFormat.QR_CODE, 400, 400);

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        MatrixToImageWriter.writeToStream(bitMatrix, "PNG", out);
        return out.toByteArray();
    }

    // Option to regenerate (invalidate previous code)
    public byte[] regenerateQRCode(Long activiteId, boolean singleUse) throws WriterException, IOException {
        // simply call generate after clearing code (overwrites previous)
        return generateQRCodeForActivity(activiteId, singleUse);
    }

    // Validate scan: actorId from client, code scanned (with or without "PRESENCE:" prefix)
    @Transactional
    public ParticipationDTO validatePresence(Long actorId, String rawCode) {
        if (rawCode == null || rawCode.isBlank()) {
            throw new IllegalArgumentException("Code manquant.");
        }

        String code = rawCode.startsWith("PRESENCE:") ? rawCode.substring("PRESENCE:".length()) : rawCode;

        SocialAction action = socialActionRepository.findByQrCode(code)
                .orElseThrow(() -> new RuntimeException("Code invalide."));

        if (action.getQrCodeExpiration() == null || Instant.now().isAfter(action.getQrCodeExpiration())) {
            throw new RuntimeException("QR code expiré.");
        }

        Actor actor = actorRepository.findById(actorId)
                .orElseThrow(() -> new RuntimeException("Acteur introuvable."));

        // Check duplicate participation
        Optional<Participation> existing = participationRepository.findByActorIdAndActiviteId(actorId, action.getId());
        if (existing.isPresent()) {
            throw new RuntimeException("L'utilisateur a déjà été enregistré comme présent.");
        }

        // create participation
        Participation p = new Participation();
        p.setActor(actor);
        p.setActivite(action);
        p.setStatus(true);
        Participation saved = participationRepository.save(p);

        // Optionally invalidate QR if single-use
        if (Boolean.TRUE.equals(action.getQrCodeSingleUse())) {
            action.setQrCode(null);
            action.setQrCodeExpiration(null);
            socialActionRepository.save(action);
        }

        return new ParticipationDTO(saved.getId(), actorId, action.getId(), true);
    }

    private String generateRandomCode(int length) {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        SecureRandom rnd = new SecureRandom();
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) sb.append(chars.charAt(rnd.nextInt(chars.length())));
        return sb.toString();
    }
}

