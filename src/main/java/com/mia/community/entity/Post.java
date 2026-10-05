package com.mia.community.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "posts")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @Column
    private String imageUrl;

    @OneToOne(mappedBy = "post", cascade =  CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private PostStat stats;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    protected Post() {}

    public Post(User user, String title, String content, String imageUrl) {
        this.user  = user;
        this.title = title;
        this.content = content;
        this.imageUrl = imageUrl;
        this.stats = new PostStat(this);
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

    public void update(String title, String content, String imageUrl) {
        this.title = title;
        this.content = content;
        this.imageUrl = imageUrl;
    }

    public Long getId() {
        return id;
    }
    public User getUser() {
        return user;
    }
    public String getTitle() {
        return title;
    }
    public String getContent() {
        return content;
    }
    public String getImageUrl() {
        return  imageUrl;
    }
    public PostStat getStats() {
        return stats;
    }
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
}
