package com.mia.community.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "comments")

public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "INT UNSIGNED")
    private Long id;

    @Column(nullable = false, columnDefinition = "INT UNSIGNED")
    private Long postId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, columnDefinition = "INT UNSIGNED")
    private User user;

    @Column(nullable = false, length = 1500)
    private String content;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    protected Comment() {}

    public Comment(Long postId, User user, String content) {
        this.postId = postId;
        this.user = user;
        this.content = content;
    }

    @PrePersist
    public void prePersist() {
        LocalDateTime now = LocalDateTime.now();
        this.createdAt = now;
        this.updatedAt = now;
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    public void update(String content) {
        this.content = content;
    }
    public Long getId() {
        return id;
    }
    public Long getPostId() {
        return postId;
    }
    public User getUser() { return user; }
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