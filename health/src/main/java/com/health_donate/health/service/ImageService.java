package com.health_donate.health.service;

package com.health_donate.health.service;

import com.health_donate.health.dto.ImageDTO;
import com.health_donate.health.entity.Image;
import com.health_donate.health.mapper.ImageMapper;
import com.health_donate.health.repository.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ImageService {

    @Autowired
    private ImageRepository imageRepository;

    // CREATE
    public ImageDTO createImage(ImageDTO dto) {
        Image image = ImageMapper.toEntity(dto);
        Image saved = imageRepository.save(image);
        return ImageMapper.toDTO(saved);
    }

    // READ
    public ImageDTO getImageById(Long id) {
        Optional<Image> opt = imageRepository.findById(id);
        return opt.map(ImageMapper::toDTO).orElse(null);
    }

    // UPDATE
    public ImageDTO updateImage(Long id, ImageDTO dto) {
        Optional<Image> opt = imageRepository.findById(id);
        if (opt.isEmpty()) return null;

        Image image = opt.get();
        image.setUrl(dto.getUrl());

        Image updated = imageRepository.save(image);
        return ImageMapper.toDTO(updated);
    }

    // DELETE
    public boolean deleteImage(Long id) {
        if (!imageRepository.existsById(id)) return false;
        imageRepository.deleteById(id);
        return true;
    }
}

