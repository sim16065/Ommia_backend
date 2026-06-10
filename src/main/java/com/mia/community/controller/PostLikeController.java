package com.mia.community.controller;

import com.mia.community.common.response.ApiResponse;
import com.mia.community.dto.postlike.response.PostLikeResponse;
import com.mia.community.service.PostLikeService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/posts")
public class PostLikeController {

    private final PostLikeService postLikeService;

    public PostLikeController(PostLikeService postLikeService) {
        this.postLikeService = postLikeService;
    }

    @PostMapping("/{postId}/likes")
    public ResponseEntity<ApiResponse<PostLikeResponse>> addLike(
            @PathVariable Long postId,
            @AuthenticationPrincipal Long userId) {
        PostLikeResponse response = postLikeService.addLike(postId, userId);
        return ResponseEntity.ok(ApiResponse.success("게시물 좋아요가 추가되었습니다.", response));
    }

    @DeleteMapping("/{postId}/likes")
    public ResponseEntity<ApiResponse<PostLikeResponse>> removeLike(
            @PathVariable Long postId,
            @AuthenticationPrincipal Long userId) {
        PostLikeResponse response = postLikeService.removeLike(postId, userId);
        return ResponseEntity.ok( ApiResponse.success("좋아요가 취소되었습니다.", response));
    }
}
