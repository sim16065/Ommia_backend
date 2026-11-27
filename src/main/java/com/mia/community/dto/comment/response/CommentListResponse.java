package com.mia.community.dto.comment.response;

import java.util.List;

public class CommentListResponse {
    private final List<CommentResponse> comments;
    private final Long nextCursor;
    private final int pageSize;
    private final boolean hasNext;

    public CommentListResponse(List<CommentResponse> comments, Long nextCursor, int pageSize, boolean hasNext) {
        this.comments = comments;
        this.nextCursor = nextCursor;
        this.pageSize = pageSize;
        this.hasNext = hasNext;
    }

    public List<CommentResponse> getComments() {
        return comments;
    }

    public Long getNextCursor() {
        return nextCursor;
    }

    public int getPageSize() {
        return pageSize;
    }

    public boolean isHasNext() {
        return hasNext;
    }
}
