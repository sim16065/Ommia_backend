package com.mia.community.dto.post.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.mia.community.entity.Post;

import java.time.LocalDateTime;

// 게시물 응답 반환 시, id가 맨앞에 오게끔 순서 명시
@JsonPropertyOrder({"id", "userId", "nickname", "profileImageUrl", "title", "content", "imageUrl", "stats", "isLiked", "createdAt", "updatedAt"})
public class PostResponse {
    private final Long postId;
    private final Long userId;
    private final String nickname;
    private final String profileImageUrl;
    private final String title;
    private final String content;
    private final String imageUrl;
    private final PostStatsResponse stats;
    private final boolean isLiked;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    public PostResponse(Post post, boolean isLiked) {
        this.postId = post.getId();
        this.userId = post.getUser().getId();
        this.nickname = post.getUser().getNickname();
        this.profileImageUrl = post.getUser().getProfileImageUrl();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.imageUrl = post.getImageUrl();

        this.stats = new PostStatsResponse(post.getLikeCount(), post.getViewCount());

        this.isLiked = isLiked;
        this.createdAt = post.getCreatedAt();
        this.updatedAt = post.getUpdatedAt();
    }

    public Long getId() { return postId; }
    public Long getUserId() { return userId; }
    public String getNickname() { return nickname; }
    public String getProfileImageUrl() { return profileImageUrl; }
    public String getTitle() { return title; }
    public String getContent() { return content; }
    public String getImageUrl() { return imageUrl; }
    public PostStatsResponse getStats() { return stats; }
    @JsonProperty("isLiked")
    public boolean isLiked() { return isLiked; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
}