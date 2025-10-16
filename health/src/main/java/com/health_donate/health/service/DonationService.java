package com.health_donate.health.service;



import com.health_donate.health.dto.DonationDTO;
import com.health_donate.health.entity.Actor;
import com.health_donate.health.entity.Donation;
import com.health_donate.health.entity.Image;
import com.health_donate.health.entity.User;
import com.health_donate.health.mapper.DonationMapper;
import com.health_donate.health.repository.ActorRepository;
import com.health_donate.health.repository.DonationRepository;
import com.health_donate.health.repository.ImageRepository;
import com.health_donate.health.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class DonationService {


    private DonationRepository donationRepository;
    private ActorRepository actorRepository;



    private final FileStorageService fileStorageService;
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

    // --- UPDATE ---
    public DonationDTO updateDonation(Long id, DonationDTO dto) {
        Optional<Donation> opt = donationRepository.findById(id);
        if (opt.isEmpty()) return null;

        Donation donation = opt.get();
        donation.setTitle(dto.getTitle());
        donation.setDescription(dto.getDescription());
        donation.setCategory(dto.getCategory());
        donation.setQuantity(dto.getQuantity());
        donation.setUrgent(dto.isUrgent());
        donation.setIsAvailable(dto.getIsAvailable());

        Donation updated = donationRepository.save(donation);
        return DonationMapper.toDTO(updated);
    }

    // --- DELETE ---
    public boolean deleteDonation(Long id) {
        if (!donationRepository.existsById(id)) return false;
        donationRepository.deleteById(id);
        return true;
    }
}

