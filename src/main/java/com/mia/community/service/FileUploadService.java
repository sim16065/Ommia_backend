package com.mia.community.service;

import com.mia.community.common.exception.CustomException;
import com.mia.community.common.exception.ErrorCode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileUploadService {
    @Value("${file.upload.local.dir}")
    private String uploadDir;

    public String upload(MultipartFile file, String directory) {
        try {
            String originalFilename = file.getOriginalFilename();
            if (originalFilename == null || !originalFilename.contains(".")) {
                throw new CustomException(ErrorCode.INVALID_FILE);
            }

            String extension = originalFilename.substring(originalFilename.lastIndexOf('.'));
            if (!extension.matches("\\.(jpg|jpeg|png|gif|webp)")) {
                throw new CustomException(ErrorCode.INVALID_IMAGE_FORMAT);
            }

            Path uploadPath = Paths.get(uploadDir, directory);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            String storedFileName = UUID.randomUUID() + extension;
            Path filePath = uploadPath.resolve(storedFileName);
            file.transferTo(filePath.toFile());

            return "/uploads/" + directory + "/" + storedFileName;
        } catch (IOException e) {
            throw new CustomException(ErrorCode.FILE_UPLOAD_FAILED);
        }


    }
}
