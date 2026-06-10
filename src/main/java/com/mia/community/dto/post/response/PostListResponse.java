package com.mia.community.dto.post.response;

import java.util.List;

public class PostListResponse {
    private final List<PostResponse> posts;
    private final int currentPage;
    private final int pageSize;
    private final long totalCount;
    private final int totalPages;
    private final boolean hasNext;

    public PostListResponse(List<PostResponse> posts, int currentPage, int pageSize, long totalCount, int totalPages, boolean hasNext) {
        this.posts = posts;
        this.currentPage = currentPage;
        this.pageSize = pageSize;
        this.totalCount = totalCount;
        this.totalPages = totalPages;
        this.hasNext = hasNext;
    }

    public List<PostResponse> getPosts() { return posts; }
    public int getCurrentPage() { return currentPage; }
    public int getPageSize() { return pageSize; }
    public long getTotalCount() { return totalCount; }
    public int getTotalPages() { return totalPages; }
    public boolean isHasNext() { return hasNext; }
}