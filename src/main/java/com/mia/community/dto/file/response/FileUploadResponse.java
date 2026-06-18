package com.mia.community.dto.file.response;

public class FileUploadResponse {
    private final String fileUrl;

    public FileUploadResponse(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public String getFileUrl() {
        return fileUrl;
    }
}
