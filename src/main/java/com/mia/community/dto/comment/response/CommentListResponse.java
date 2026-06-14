package com.mia.community.dto.comment.response;

import java.util.List;

public class CommentListResponse {
    private final List<CommentResponse> data;
    private final Long nextCursor;
    private final int pageSize;
    private final boolean hasNext;

    public CommentListResponse(List<CommentResponse> data, Long nextCursor, int pageSize, boolean hasNext) {
        this.data = data;
        this.nextCursor = nextCursor;
        this.pageSize = pageSize;
        this.hasNext = hasNext;
    }

    public List<CommentResponse> getData() {
        return data;
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
