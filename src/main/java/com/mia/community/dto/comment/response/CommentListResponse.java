package com.mia.community.dto.comment.request;

public class CommentListResponse {
    private final List<CommentResponse> data;
    private final CommentPaginationResponse pagination;

    public CommentListResponse(List<CommentResponse> data, CommentPaginationResponse pagination) {
        this.data = data;
        this.pagination = pagination;
    }

    public List<CommentResponse> getData() { return data; }
    public CommentPaginationResponse getPagination() { return pagination; }
}
