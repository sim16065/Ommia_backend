package com.mia.community.dto.comment.response;

public class CommentPaginationResponse {
    private final Long nextCursor;
    private final int pageSize;
    private final boolean hasNext;

    public CommentPaginationResponse(Long nextCursor, int pageSize, boolean hasNext) {
        this.nextCursor = nextCursor;
        this.pageSize = pageSize;
        this.hasNext = hasNext;
    }

    public Long getNextCursor() { return nextCursor; }
    public int getPageSize() { return pageSize; }
    public boolean isHasNext() { return hasNext; }
}