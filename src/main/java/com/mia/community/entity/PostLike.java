package com.mia.community.entity;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "post_likes")
@IdClass(PostLike.PostLikeId.class) // 복합키 클래스 지정
public class PostLike {

    @Id
    @Column(nullable = false)
    private Long userId;

    @Id
    @Column(nullable = false)
    private Long postId;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    protected PostLike() {}

    // 좋아요 추가 생성자
    public PostLike(Long userId, Long postId) {
        this.userId = userId;
        this.postId = postId;
    }

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
    }

    public Long getUserId() { return userId; }
    public Long getPostId() { return postId; }
    public LocalDateTime getCreatedAt() { return createdAt; }

    // userId와 postId를 묶어서 PK로 사용
    public static class PostLikeId implements Serializable {
        private Long userId;
        private Long postId;

        public PostLikeId() {}

        // JPA 복합키 식별자로 사용하는 키 클래스 생성자
        public PostLikeId(Long userId, Long postId) {
            this.userId = userId;
            this.postId = postId;
        }
        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }

            if (!(o instanceof PostLikeId that)) {
                return false;
            }

            return Objects.equals(userId, that.userId)
                    && Objects.equals(postId, that.postId);
        }

        @Override
        public int hashCode() {
            return Objects.hash(userId, postId);
        }
    }
}
