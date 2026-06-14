package com.mia.community.dto.postlike.response;

public class PostLikeResponse {

    private final boolean liked;
    private final long likeCount;

    public PostLikeResponse(boolean liked, long likeCount) {
        this.liked = liked;
        this.likeCount = likeCount;
    }

    public boolean isLiked() {
        return liked;
    }

    public  long getLikeCount() {
        return likeCount;
    }
}
