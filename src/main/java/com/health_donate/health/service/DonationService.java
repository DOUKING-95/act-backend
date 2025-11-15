package com.health_donate.health.service;



import com.health_donate.health.dto.DonationDTO;
import com.health_donate.health.dto.TopDonorDTO;
import com.health_donate.health.entity.Actor;
import com.health_donate.health.entity.Donation;
import com.health_donate.health.entity.Image;
import com.health_donate.health.entity.User;
import com.health_donate.health.enumT.DonationStatus;
import com.health_donate.health.mapper.DonationMapper;
import com.health_donate.health.repository.ActorRepository;
import com.health_donate.health.repository.DonationRepository;
import com.health_donate.health.repository.ImageRepository;
import com.health_donate.health.repository.UserRepository;
import com.health_donate.health.service.external.CloudinaryService;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class DonationService {


    private DonationRepository donationRepository;
    private ActorRepository actorRepository;



    private final CloudinaryService fileStorageService;
    private final ImageRepository imageRepository;

    // --- CREATE ---
    public DonationDTO createDonationWithImages(DonationDTO dto, MultipartFile[] images) throws IOException {

        Actor actor = actorRepository.findById(dto.getDonorId())
                .orElseThrow(() -> new EntityNotFoundException("Pas d'acteur trouvé pour cette id " + dto.getDonorId()));


        Donation donation = DonationMapper.toEntity(dto, actor);
        Donation savedDonation = donationRepository.save(donation);


        List<Image> savedImages = new ArrayList<>();
        for (MultipartFile file : images) {
            String path = fileStorageService.storeFile(file);

            Image img = new Image();
            img.setUrl(path);
            img.setName(file.getOriginalFilename());
            img.setDonation(savedDonation);
            savedImages.add(imageRepository.save(img));
        }


        savedDonation.setImages(savedImages);
        donationRepository.save(savedDonation);

        return DonationMapper.toDTO(savedDonation);
    }


    // --- READ ---
    public DonationDTO getDonationById(Long id) {
        Optional<Donation> opt = donationRepository.findById(id);
        return opt.map(DonationMapper::toDTO).orElse(null);
    }

    // --- GET ALL ---
    public List<DonationDTO> getAllDonations() {
        List<Donation> donations = donationRepository.findByUrgent(true);
        return donations.stream()
                .map(DonationMapper::toDTO)
                .collect(Collectors.toList());
    }

    public Page<DonationDTO> getDonationsByActorPaged(Long donorId, int page) {
        int size = 5;

        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "id"));

        Page<Donation> donationsPage = donationRepository.findByDonorId(donorId, pageRequest);

        return donationsPage.map(DonationMapper::toDTO);
    }

    public Page<DonationDTO> getAllDonationsPaged(int page) {
        int size = 5;
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "id"));

        Page<Donation> donationsPage = donationRepository.findAllByIsAvailable(DonationStatus.PUBLIE,pageRequest);


        return donationsPage.map(DonationMapper::toDTO);
    }


    // --- UPDATE ---
    public DonationDTO updateDonation(Long id, DonationDTO dto, MultipartFile[] images) throws IOException {
        // Vérifier si la donation existe
        Donation donation = donationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Donation introuvable pour l'id " + id));

        //  Mettre à jour les champs
        donation.setTitle(dto.getTitle());
        donation.setDescription(dto.getDescription());
        donation.setCategory(dto.getCategory());
        donation.setQuantity(dto.getQuantity());
        donation.setUrgent(dto.isUrgent());
        donation.setIsAvailable(dto.getIsAvailable());
        donation.setLocation(dto.getLocation());
        donation.setPublished(dto.isPublished());


        Donation updatedDonation = donationRepository.save(donation);


        if (images != null && images.length > 0) {

            List<Image> oldImages = imageRepository.findByDonationId(updatedDonation.getId());
            imageRepository.deleteAll(oldImages);

            List<Image> savedImages = new ArrayList<>();
            for (MultipartFile file : images) {
                String path = fileStorageService.storeFile(file);

                Image img = new Image();
                img.setUrl(path);
                img.setName(file.getOriginalFilename());
                img.setDonation(updatedDonation);
                savedImages.add(imageRepository.save(img));
            }
            updatedDonation.setImages(savedImages);
        }

        updatedDonation = donationRepository.save(updatedDonation);

        return DonationMapper.toDTO(updatedDonation);
    }


    public List<TopDonorDTO> getTop15Donors() {
        List<Object[]> results = donationRepository.findTop15Donors(DonationStatus.PUBLIE);
        return results.stream()
                .map(obj -> new TopDonorDTO(
                        ((Number) obj[0]).longValue(),
                        ((Number) obj[1]).longValue()))
                .limit(15)
                .toList();
    }


    // --- DELETE ---
    public boolean deleteDonation(Long id) {
        if (!donationRepository.existsById(id)) return false;
        donationRepository.deleteById(id);
        return true;
    }
}

