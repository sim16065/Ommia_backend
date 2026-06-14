package com.mia.community.dto.post.response;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.time.LocalDateTime;

// 게시물 응답 반환 시, id가 맨앞에 오게끔 순서 명시
@JsonPropertyOrder({"id", "userId", "nickname", "title", "content", "imageUrl", "stats", "createdAt", "updatedAt"})
public class PostResponse {

    private final Long postId;
    private final Long userId;
    private final String nickname;
    private final String title;
    private final String content;
    private final String imageUrl;
    private final PostStatsResponse stats;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    public PostResponse(Long postId, Long userId, String nickname, String title, String content, String imageUrl, PostStatsResponse stats,
                        LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.postId = postId;
        this.userId = userId;
        this.nickname = nickname;
        this.title = title;
        this.content = content;
        this.imageUrl = imageUrl;
        this.stats = stats;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Long getId() { return postId; }
    public Long getUserId() { return userId; }
    public String getNickname() { return nickname; }
    public String getTitle() { return title; }
    public String getContent() { return content; }
    public String getImageUrl() { return imageUrl; }
    public PostStatsResponse getStats() { return stats; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
}