package com.health_donate.health.service;

import com.health_donate.health.dto.DTOAdmin.DonDTO;
import com.health_donate.health.entity.*;
import com.health_donate.health.enumT.Destinataire;
import com.health_donate.health.enumT.DonationStatus;
import com.health_donate.health.mapper.DonMapper;
import com.health_donate.health.repository.*;
import com.health_donate.health.service.external.CloudinaryService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DonService {

    private final DonationRepository donationRepository;
    private final ActorRepository actorRepository;
    private final ImageRepository imageRepository;
    private final CloudinaryService fileStorageService;
    private final UserRepository userRepository;
    private final NotificationRepository notificationRepository;
    private final ReceptionRepository receptionRepository;


    // --- CREATE ---
    public DonDTO createDonationWithImages(DonDTO dto, MultipartFile[] images) throws IOException {

        Actor actor = actorRepository.findById(dto.getDonorId())
                .orElseThrow(() -> new EntityNotFoundException("Aucun acteur trouvé pour l'ID " + dto.getDonorId()));

        Donation donation = DonMapper.toEntity(dto, actor);
        Donation savedDonation = donationRepository.save(donation);
//
//        List<Image> savedImages = saveImages(images, savedDonation);
//        savedDonation.setImages(savedImages);

        return DonMapper.toDTO(donationRepository.save(savedDonation));
    }

    // --- GET ONE ---
    public DonDTO getDonationById(Long id) {
        Donation donation = donationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Donation non trouvée avec l'id " + id));
        return DonMapper.toDTO(donation);
    }

    // --- GET ALL ---
    public List<DonDTO> getAllDonations() {
        return donationRepository.findAll().stream()
                .map(DonMapper::toDTO)
                .collect(Collectors.toList());
    }

    // --- UPDATE ---
    public DonDTO updateDonation(Long id, DonDTO dto, MultipartFile[] images) throws IOException {
        Donation donation = donationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Donation non trouvée avec l'id " + id));

        // MàJ des champs
        donation.setTitle(dto.getTitle());
        donation.setDescriptionCourte(dto.getDescriptionCourte());
        donation.setDescriptionComplete(dto.getDescription());
        donation.setCategory(dto.getCategory());
        donation.setQuantity(dto.getQuantity());
        donation.setIsAvailable(dto.getIsAvailable());
        donation.setLocation(dto.getLocation());
        donation.setUrgent(dto.isUrgent());
        donation.setPublished(dto.isPublished());
        donation.setEtat(dto.getEtat());
        donation.setTypeDon(dto.getTypeDon());
        donation.setRaisonDeclin(dto.getRaisonDeclin());

        if (images != null && images.length > 0) {
            imageRepository.deleteAll(imageRepository.findByDonationId(donation.getId()));
            donation.setImages(saveImages(images, donation));
        }

        return DonMapper.toDTO(donationRepository.save(donation));
    }




    //Publier un don
    public DonDTO publishDonation(Long id) {
        Donation donation = donationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Don introuvable"));
        donation.setIsAvailable(DonationStatus.PUBLIE);
        donationRepository.save(donation);
        return DonMapper.toDTO(donation);
    }

    //Décliner un don avec raison
    public DonDTO declineDonation(Long id, String reason) {
        Donation donation = donationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Don introuvable"));
        donation.setRaisonDeclin(reason);
        donation.setIsAvailable(DonationStatus.DECLINE);
        donationRepository.save(donation);
        return DonMapper.toDTO(donation);
    }

    //Revoquer un don avec raison
    public DonDTO revoqueDonation(Long id, String reason) {
        Donation donation = donationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Don introuvable"));
        donation.setRaisonDeclin(reason);
        donation.setIsAvailable(DonationStatus.EN_ATTENTE);
        donationRepository.save(donation);
        return DonMapper.toDTO(donation);
    }


    //Attribuer et marquer comme livré
    public DonDTO assignAndDeliverDonation(Long id, Long beneficiaryId) {
        Donation donation = donationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Don introuvable"));
        Actor beneficiary = actorRepository.findById(beneficiaryId)
                .orElseThrow(() -> new EntityNotFoundException("Bénéficiaire introuvable"));

        donation.setIsAvailable(DonationStatus.LIVRE);
        donationRepository.save(donation);
        return DonMapper.toDTO(donation);
    }

    //Modifier attribution
    public DonDTO modifyAssignment(Long id, Long newBeneficiaryId) {
        Donation donation = donationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Don introuvable"));
        Actor newBeneficiary = actorRepository.findById(newBeneficiaryId)
                .orElseThrow(() -> new EntityNotFoundException("Nouveau bénéficiaire introuvable"));

        // ici tu pourrais changer une propriété donationRequest ou un champ spécifique
        donation.setIsAvailable(DonationStatus.LIVRE);
        donationRepository.save(donation);
        return DonMapper.toDTO(donation);
    }

    //Notifier (placeholder pour email/SMS)
    public DonDTO notifyDonation(Long id,String message,Long donorID) {
        Donation donation = donationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Don introuvable"));
        User donor = userRepository.findById(donorID)
                .orElseThrow(() -> new EntityNotFoundException("donateur introuvable"));

        Notification notif = new Notification(
                null,
                "Alerte de recuperation de don",
                "Alerte",
                message,
                Destinataire.benevoles,
                LocalDate.now().atStartOfDay(),
                null
        );
        notificationRepository.save(notif);
        Reception reception = new Reception(
                null,
                false,
                null,
                null,
                notif,
                donor
        );
        receptionRepository.save(reception);

        //La logique pour l'envoie a travers mail et sms:


        System.out.println("Notification envoyée pour le don : " + donation.getTitle());
        return DonMapper.toDTO(donation);
    }



    // --- DELETE ---
    public boolean deleteDonation(Long id) {
        if (!donationRepository.existsById(id)) return false;
        donationRepository.deleteById(id);
        return true;
    }

    // --- UTIL ---
    private List<Image> saveImages(MultipartFile[] images, Donation donation) throws IOException {
        List<Image> savedImages = new ArrayList<>();
        for (MultipartFile file : images) {
            String path = fileStorageService.storeFile(file);
            Image img = new Image();
            img.setUrl(path);
            img.setName(file.getOriginalFilename());
            img.setDonation(donation);
            savedImages.add(imageRepository.save(img));
        }
        return savedImages;
    }
}
