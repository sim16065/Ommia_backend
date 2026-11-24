package com.mia.community.dto.comment.response;

import com.mia.community.dto.user.response.AuthorResponse;
import com.mia.community.entity.Comment;

import java.time.LocalDateTime;

public class CommentResponse {
    private final Long id;
    private final Long postId;
    private final AuthorResponse author;
    private final String content;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    public CommentResponse(Comment comment) {
        this.id = comment.getId();
        this.postId = comment.getPostId();
        this.author = AuthorResponse.from(comment.getUser());
        this.content = comment.getContent();
        this.createdAt = comment.getCreatedAt();
        this.updatedAt = comment.getUpdatedAt();
    }

    public static CommentResponse from(Comment comment) {
        return new CommentResponse(comment);
    }

    public Long getId() {
        return id;
    }
    public Long getPostId() {
        return postId;
    }
    public AuthorResponse getAuthor() {
        return author;
    }
    public String getContent() {
        return content;
    }
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
}