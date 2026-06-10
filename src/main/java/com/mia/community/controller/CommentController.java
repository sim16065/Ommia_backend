package com.community.comment.controller;

import com.community.common.response.ApiResponse;
import com.community.comment.dto.request.CommentCreateRequest;
import com.community.comment.dto.request.CommentUpdateRequest;
import com.community.comment.dto.response.CommentResponse;
import com.community.comment.service.CommentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/posts/{postId}/comments")
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<CommentResponse>>> getComments(
            @PathVariable Long postId
    ) {
        try {
            List<CommentResponse> response = commentService.getComments(postId);

            return ResponseEntity.ok(
                    new ApiResponse<>("get_comments_success", response)
            );
        } catch (IllegalArgumentException e) {
            return ResponseEntity
                    .status(404)
                    .body(new ApiResponse<>("post_not_found", null));
        }
    }

    @PostMapping
    public ResponseEntity<ApiResponse<CommentResponse>> createComment(
            @PathVariable Long postId,
            @RequestBody CommentCreateRequest request
    ) {
        try {
            CommentResponse response = commentService.createComment(postId, request);

            return ResponseEntity
                    .status(201)
                    .body(new ApiResponse<>("create_comment_success", response));
        } catch (IllegalArgumentException e) {
            return ResponseEntity
                    .status(404)
                    .body(new ApiResponse<>("post_not_found", null));
        }
    }

    @PatchMapping("/{commentId}")
    public ResponseEntity<ApiResponse<CommentResponse>> updateComment(
            @PathVariable Long postId,
            @PathVariable Long commentId,
            @RequestBody CommentUpdateRequest request
    ) {
        try {
            CommentResponse response = commentService.updateComment(postId, commentId, request);

            return ResponseEntity.ok(
                    new ApiResponse<>("update_comment_success", response)
            );
        } catch (IllegalArgumentException e) {
            return ResponseEntity
                    .status(404)
                    .body(new ApiResponse<>("comment_not_found", null));
        }
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<ApiResponse<Void>> deleteComment(
            @PathVariable Long postId,
            @PathVariable Long commentId
    ) {
        try {
            commentService.deleteComment(postId, commentId);

            return ResponseEntity.ok(
                    new ApiResponse<>("delete_comment_success", null)
            );
        } catch (IllegalArgumentException e) {
            return ResponseEntity
                    .status(404)
                    .body(new ApiResponse<>("comment_not_found", null));
        }
    }
}