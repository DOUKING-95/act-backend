package com.health_donate.health.service.external;



import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Service
public class CloudinaryService {

    private final Cloudinary cloudinary;
    private final String folder;

    public CloudinaryService(Cloudinary cloudinary) {
        this.cloudinary = cloudinary;
        // tu peux lire le folder depuis propriétés si tu veux
        this.folder = "health_donate"; // ou injecte via @Value("${cloudinary.folder}")
    }

    public String upload(MultipartFile file) {
        try {
            Map<?, ?> uploadResult = cloudinary.uploader()
                    .upload(file.getBytes(), ObjectUtils.asMap(
                            "folder", folder,
                            "resource_type", "auto" // auto détecte images/videos
                    ));

            // secure_url contient l'URL HTTPS publique
            return uploadResult.get("secure_url").toString();
        } catch (IOException e) {
            throw new RuntimeException("Erreur upload Cloudinary: " + e.getMessage(), e);
        }
    }

    public boolean deleteByPublicId(String publicId) {
        try {
            Map<?, ?> result = cloudinary.uploader().destroy(publicId, ObjectUtils.emptyMap());
            // result contient "result":"ok" quand supprimé
            return "ok".equals(result.get("result"));
        } catch (Exception e) {
            return false;
        }
    }
}

 /**   Map<?, ?> uploadResult = cloudinary.uploader().upload(...);
        String url = uploadResult.get("secure_url").toString();
        String publicId = uploadResult.get("public_id").toString();
*/