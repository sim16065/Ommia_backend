package com.mia.community.controller;

import com.mia.community.common.response.ApiResponse;
import com.mia.community.dto.file.response.FileUploadResponse;
import com.mia.community.dto.user.response.ProfileImageResponse;
import com.mia.community.service.FileUploadService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/files")
public class FileUploadController {
    private final FileUploadService fileUploadService;

    public FileUploadController(FileUploadService fileUploadService) {
        this.fileUploadService = fileUploadService;
    }

    @PostMapping("/profile")
    public ResponseEntity<ApiResponse<ProfileImageResponse>> uploadProfileImage(
                @RequestParam("profileImage")MultipartFile file) {
            String fileUrl = fileUploadService.uploadFile(file, "profile");
            return ResponseEntity.ok(ApiResponse.success("프로필 이미지가 업로드되었습니다.",
                    new ProfileImageResponse(fileUrl)));
        }
    @PostMapping("/post")
    public ResponseEntity<ApiResponse<FileUploadResponse>> uploadPostImage(
                @RequestParam("postImage") MultipartFile file) {
        String fileUrl = fileUploadService.uploadFile(file, "post");
        return ResponseEntity.ok(ApiResponse.success("게시물 이미지가 업로드되었습니다.", new FileUploadResponse(fileUrl)));
    }
}
