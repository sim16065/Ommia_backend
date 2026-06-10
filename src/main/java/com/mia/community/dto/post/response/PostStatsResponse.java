package com.mia.community.dto.post.response;

public class PostStatsResponse {
    private final long likeCount;
    private final long viewCount;

    public PostStatsResponse(long likeCount, long viewCount) {
        this.likeCount = likeCount;
        this.viewCount = viewCount;
    }

    public long getLikeCount() { return likeCount; }
    public long getViewCount() { return viewCount; }
}

