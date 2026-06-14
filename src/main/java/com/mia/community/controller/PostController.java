package com.mia.community.controller;

import com.mia.community.common.response.ApiResponse;
import com.mia.community.dto.post.request.PostCreateRequest;
import com.mia.community.dto.post.request.PostUpdateRequest;
import com.mia.community.dto.post.response.PostListResponse;
import com.mia.community.dto.post.response.PostResponse;
import com.mia.community.service.PostService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<PostResponse>> createPost(
            @AuthenticationPrincipal Long userId,
            @RequestBody @Valid PostCreateRequest request) {
        PostResponse response = postService.createPost(userId, request);
        return ResponseEntity.status(201)
                .body(ApiResponse.success("게시물이 등록되었습니다.", response));
    }

    // 게시물 전체 조회
    @GetMapping
    public ResponseEntity<ApiResponse<PostListResponse>> getAllPosts(
            @RequestParam(defaultValue = "1") int page) {
        PostListResponse response = postService.getAllPosts(page);
        return ResponseEntity.ok( ApiResponse.success("게시물 목록을 성공적으로 불러왔습니다.",  response));
    }

    // 특정 게시물 조회
    @GetMapping("/{postId}")
    public ResponseEntity<ApiResponse<PostResponse>> getPost(@PathVariable Long postId) {
            PostResponse response = postService.getPost(postId);
            return ResponseEntity.ok(ApiResponse.success("게시물을 성공적으로 불러왔습니다.", response));
    }

    @PatchMapping ("/{postId}")
    public ResponseEntity<ApiResponse<PostResponse>> updatePost(
            @PathVariable Long postId,
            @AuthenticationPrincipal Long userId,
            @RequestBody PostUpdateRequest request) {
            PostResponse response = postService.updatePost(postId, userId, request);
            return ResponseEntity.ok(ApiResponse.success("게시물이 수정되었습니다.", response));
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<ApiResponse<Void>> deletePost(
            @PathVariable Long postId,
            @AuthenticationPrincipal Long userId) {
            postService.deletePost(postId, userId);
            return ResponseEntity.ok(ApiResponse.success("게시물이 삭제되었습니다.", null));
    }
}