package com.mia.community.dto.comment.response;

import com.mia.community.entity.Comment;

import java.time.LocalDateTime;

public class CommentResponse {
    private Long Id;
    private Long postId;
    private Long userId;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public CommentResponse(Long commentId, Long postId, Long userId, String content, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.Id = commentId;
        this.postId = postId;
        this.userId = userId;
        this.content = content;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static CommentResponse from(Comment comment) {
        return new CommentResponse(
                comment.getId(),
                comment.getPostId(),
                comment.getUserId(),
                comment.getContent(),
                comment.getCreatedAt(),
                comment.getUpdatedAt()
        );
    }

    public Long getCommentId() {
        return Id;
    }
    public Long getPostId() {
        return postId;
    }
    public Long getUserId() { return userId; }
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