package com.mia.community.controller;

import com.mia.community.common.response.ApiResponse;
import com.mia.community.dto.comment.request.CommentCreateRequest;
import com.mia.community.dto.comment.request.CommentUpdateRequest;
import com.mia.community.dto.comment.response.CommentListResponse;
import com.mia.community.dto.comment.response.CommentResponse;
import com.mia.community.service.CommentService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/posts/{postId}/comments")
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<CommentListResponse>> getComments(
            @PathVariable Long postId,
            @RequestParam(required = false) Long cursor,
            @RequestParam(defaultValue = "10") int pageSize) {
        CommentListResponse response = commentService.getComments(postId, cursor, pageSize);
        return ResponseEntity.ok( ApiResponse.success("댓글을 성공적으로 불러왔습니다.", response));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<CommentResponse>> createComment(
            @PathVariable Long postId,
            @AuthenticationPrincipal Long userId,
            @RequestBody CommentCreateRequest request) {
        CommentResponse response = commentService.createComment(postId, userId, request);
        return ResponseEntity.status(201).body(ApiResponse.success("댓글이 등록되었습니다.", response));
    }

    @PatchMapping("/{commentId}")
    public ResponseEntity<ApiResponse<CommentResponse>> updateComment(
            @PathVariable Long postId,
            @PathVariable Long commentId,
            @AuthenticationPrincipal Long userId,
            @RequestBody CommentUpdateRequest request) {
        CommentResponse response = commentService.updateComment(postId, commentId, userId, request);
        return ResponseEntity.ok(ApiResponse.success("댓글이 수정되었습니다.", response));
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<ApiResponse<Void>> deleteComment(
            @PathVariable Long postId,
            @PathVariable Long commentId,
            @AuthenticationPrincipal Long userId) {
       commentService.deleteComment(postId, commentId, userId);
       return ResponseEntity.ok(ApiResponse.success("댓글이 삭제되었습니다.", null));
    }
}