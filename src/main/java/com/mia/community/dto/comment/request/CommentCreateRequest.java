package com.community.comment.dto.request;

public class CommentCreateRequest {
    private String content;
    private String author;

    public CommentCreateRequest() {
    }

    public String getContent() {
        return content;
    }

    public String getAuthor() {
        return author;
    }
}