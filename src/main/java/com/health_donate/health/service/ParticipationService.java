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
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
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


    // ⏱ Durée de validité du code (en secondes)
    private final long CODE_VALIDITY_SECONDS = 600; // 10 minutes

    // Stockage temporaire : code -> {activiteId, expiration}
    private final Map<String, CodeInfo> activeCodes = new HashMap<>();

    // Classe interne pour stocker l’ID de l’activité et expiration
    private static class CodeInfo {
        Long activiteId;
        Instant expiration;

        CodeInfo(Long activiteId, Instant expiration) {
            this.activiteId = activiteId;
            this.expiration = expiration;
        }
    }

    /**
     * Génère un QR code aléatoire pour une activité
     */
    public byte[] generateQRCode(Long activiteId) throws WriterException, IOException, IOException {
        String code = generateRandomCode(8);
        Instant expiration = Instant.now().plusSeconds(CODE_VALIDITY_SECONDS);
        activeCodes.put(code, new CodeInfo(activiteId, expiration));

        String qrContent = "PRESENCE:" + code; // Le code aléatoire
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(qrContent, BarcodeFormat.QR_CODE, 300, 300);

        ByteArrayOutputStream pngOutputStream = new ByteArrayOutputStream();
        MatrixToImageWriter.writeToStream(bitMatrix, "PNG", pngOutputStream);
        return pngOutputStream.toByteArray();
    }

    /**
     * Vérifie et enregistre la présence d’un membre via le code QR
     */
    public ParticipationDTO enregistrerPresence(Long actorId, String code) {
        CodeInfo codeInfo = activeCodes.get(code);

        if (codeInfo == null || Instant.now().isAfter(codeInfo.expiration)) {
            throw new RuntimeException("Code invalide ou expiré.");
        }

        Long activiteId = codeInfo.activiteId;

        Actor actor = actorRepository.findById(actorId)
                .orElseThrow(() -> new RuntimeException("Acteur introuvable."));
        SocialAction activite = socialActionRepository.findById(activiteId)
                .orElseThrow(() -> new RuntimeException("Activité introuvable."));

        // Vérifie si le membre a déjà validé
        Optional<Participation> existing = participationRepository.findByActorIdAndActiviteId(actorId, activiteId);
        if (existing.isPresent()) {
            throw new RuntimeException("L'utilisateur a déjà été enregistré comme présent.");
        }

        Participation participation = new Participation();
        participation.setActor(actor);
        participation.setActivite(activite);
        participation.setStatus(true);

        Participation saved = participationRepository.save(participation);

        // ⚠️ Optionnel : supprimer le code après utilisation pour plus de sécurité
        activeCodes.remove(code);

        return new ParticipationDTO(saved.getId(), actorId, activiteId, true);
    }

    /**
     * Génère un code aléatoire
     */
    private String generateRandomCode(int length) {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random = new Random();
        StringBuilder sb = new StringBuilder(length);
        for(int i=0; i<length; i++) {
            sb.append(chars.charAt(random.nextInt(chars.length())));
        }
        return sb.toString();
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

    /**
     * Nettoyage automatique des codes expirés toutes les minutes
     */
    @Scheduled(fixedRate = 60000)
    private void cleanupExpiredCodes() {
        Instant now = Instant.now();
        activeCodes.entrySet().removeIf(entry -> now.isAfter(entry.getValue().expiration));
    }



    // Vérifier si un user a déjà participé
    public boolean hasParticipated(Long actorId, Long activiteId) {
        return participationRepository.existsByActorIdAndActiviteId(actorId, activiteId);
    }

    // Compter les participations validées d'une activité
    public long countParticipations(Long activiteId) {
        return participationRepository.countByActiviteIdAndStatus(activiteId, false);
    }
}

