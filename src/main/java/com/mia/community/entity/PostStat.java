package com.mia.community.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "post_stats")
public class PostStat {

    @Id
    private Long postId;

    @MapsId
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    @Column(nullable = false)
    private long viewCount = 0;

    @Column(nullable = false)
    private long likeCount = 0;

    protected PostStat() {}

    public PostStat(Post post) {
        this.post = post;
    }

    public Long getPostId() { return postId; }
    public Post getPost() { return post; }
    public Long getViewCount() { return viewCount; }
    public Long getLikeCount() { return likeCount; }
}
