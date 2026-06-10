package com.mia.community.dto.post.request;

import com.mia.community.common.ValidationMessage;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class PostUpdateRequest {

    @NotBlank(message = ValidationMessage.TITLE_REQUIRED)
    @Size(max = 26, message = ValidationMessage.TITLE_SIZE)
    private String title;

    @NotBlank(message = ValidationMessage.CONTENT_REQUIRED)
    private String content;

    private String imageUrl;

    public PostUpdateRequest() {
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}