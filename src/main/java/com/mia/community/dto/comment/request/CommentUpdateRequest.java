package com.mia.community.dto.comment.request;

import com.mia.community.common.ValidationMessage;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class CommentUpdateRequest {

    @NotBlank(message = ValidationMessage.CONTENT_REQUIRED)
    @Size(max = 1500, message = ValidationMessage.COMMENT_SIZE)
    private String content;

    public CommentUpdateRequest() {
    }

    public String getContent() {
        return content;
    }
}
