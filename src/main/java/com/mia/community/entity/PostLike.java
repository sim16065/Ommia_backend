package com.mia.community.domain;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "post_likes")
@IdClass(PostLike.PostLikeId.class)
public class PostLike {

    @Id
    @Column(nullable = false)
    private Long userId;

    @Id
    @Column(nullable = false)
    private Long postId;

    private LocalDateTime createdAt;

    public PostLike() {}

    public PostLike(Long postId, Long userId) {
        this.postId = postId;
        this.userId = userId;
        this.createdAt = LocalDateTime.now();
    }

    public Long getUserId() { return userId; }
    public Long getPostId() { return postId; }
    public LocalDateTime getCreatedAt() { return createdAt; }

    public  static  class PostLikeId implements Serializable {
        private Long userId;
        private Long postId;

        public PostLikeId() {}
        public PostLikeId(Long userId, Long postId) {
            this.userId = userId;
            this.postId = postId;
        }
    }
}
