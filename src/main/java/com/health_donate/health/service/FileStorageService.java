//package com.health_donate.health.service;
//
//
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Service;
//import org.springframework.util.StringUtils;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.io.IOException;
//import java.nio.file.Files;
//import java.nio.file.Path;
//import java.nio.file.Paths;
//import java.util.Objects;
//
//@Service
//public class FileStorageService {
//
////    @Value("${file.upload-dir}")
////    private String uploadDir;
//    private final Path rootLocation = Paths.get(System.getProperty("user.dir") + "/uploads");
//
////    public String storeFile(MultipartFile file) throws IOException {
////        // Créer le répertoire s'il n'existe pas
////        Path uploadPath = Paths.get(uploadDir);
////        if (!Files.exists(uploadPath)) {
////            Files.createDirectories(uploadPath);
////        }
////
////        // Enregistrer le fichier
////        String fileName = file.getOriginalFilename();
////        Path filePath = uploadPath.resolve(fileName);
////        file.transferTo(filePath.toFile());
////
////        // Retourner le chemin absolu (tu peux adapter ici pour générer un lien HTTP si tu partages ton dossier)
////        return filePath.toAbsolutePath().toString();
////    }
//    public String storeFile(MultipartFile file) {
//        try {
//
//            Files.createDirectories(rootLocation);
//
//            String filename = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
//
//            Path destinationFile = this.rootLocation.resolve(filename).normalize();
//
//            file.transferTo(destinationFile.toFile());
//
//            return filename;
//        } catch (Exception e) {
//            throw new RuntimeException("Impossible d'enregistrer le fichier : " + e.getMessage());
//        }
//    }
//}
//
