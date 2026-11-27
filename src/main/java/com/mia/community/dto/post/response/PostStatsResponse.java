package com.mia.community.dto.post.response;

public class PostStatsResponse {
    private final long likeCount;
    private final long viewCount;
    private final long commentCount;

    public PostStatsResponse(long likeCount, long viewCount, long commentCount) {
        this.likeCount = likeCount;
        this.viewCount = viewCount;
        this.commentCount = commentCount;
    }

    public long getLikeCount() { return likeCount; }
    public long getViewCount() { return viewCount; }
    public long getCommentCount() { return commentCount; }
}

