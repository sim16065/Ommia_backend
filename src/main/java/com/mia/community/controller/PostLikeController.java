package com.community.like.controller;

import com.community.common.response.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

public class PostLikeController {
    @PostMapping("/{postId}/likes")
    public ResponseEntity<ApiResponse<?>> addLike(@PathVariable Long postId) {
        Long userId = 1L;  // 임시
        postService.addLike(postId, userId);
        return ResponseEntity.ok(new ApiResponse<>("게시물을 좋아합니다.", null));
    }

    @DeleteMapping("/{postId}/likes")
    public ResponseEntity<ApiResponse<?>> removeLike(@PathVariable Long postId) {
        Long userId = 1L;  // 임시
        postService.removeLike(postId, userId);
        return ResponseEntity.ok(new ApiResponse<>("좋아요가 취소되었습니다.", null));
    }
}
