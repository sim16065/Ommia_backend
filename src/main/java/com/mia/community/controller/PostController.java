package com.community.post.controller;

import com.community.common.response.ApiResponse;
import com.community.post.dto.request.PostCreateRequest;
import com.community.post.dto.request.PostUpdateRequest;
import com.community.post.dto.response.PostResponse;
import com.community.post.service.PostService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<PostResponse>> createPost(@RequestBody PostCreateRequest request) {
        PostResponse response = postService.createPost(request);
        return ResponseEntity
                .status(201)
                .body(new ApiResponse<>("게시물이 등록되었습니다.", response));
    }

    // 게시물 전체 조회
    @GetMapping
    public ResponseEntity<ApiResponse<List<PostResponse>>> getAllPosts(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "10") int size) {
        List<PostResponse> response = postService.getAllPosts(page, size);
        return ResponseEntity.ok(new ApiResponse<>("게시물 목록을 성공적으로 불러왔습니다.", response));
    }

    // 특정 게시물 조회
    @GetMapping("/{postId}")
    public ResponseEntity<ApiResponse<PostResponse>> getPost(@PathVariable Long postId) {
        try {
            PostResponse response = postService.getPost(postId);
            return ResponseEntity.ok(new ApiResponse<>("게시물을 성공적으로 불러왔습니다.", response));
        } catch (IllegalArgumentException e) {
            return ResponseEntity
                    .status(404)
                    .body(new ApiResponse<>("존재하지 않거나 삭제된 게시물입니다.", null));
        }
    }

    @PutMapping("/{postId}")
    public ResponseEntity<ApiResponse<PostResponse>> updatePost(
            @PathVariable Long postId,
            @RequestBody PostUpdateRequest request
    ) {
        try {
            PostResponse response = postService.updatePost(postId, request);
            return ResponseEntity.ok(new ApiResponse<>("게시물이 수정되었습니다.", response));
        } catch (IllegalArgumentException e) {
            return ResponseEntity
                    .status(404)
                    .body(new ApiResponse<>("존재하지 않거나 삭제된 게시물입니다.", null));
        }
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<ApiResponse<Void>> deletePost(@PathVariable Long postId) {
        try {
            postService.deletePost(postId);

            return ResponseEntity.ok(
                    new ApiResponse<>("게시물이 삭제되었습니다.", null)
            );
        } catch (IllegalArgumentException e) {
            return ResponseEntity
                    .status(404)
                    .body(new ApiResponse<>("존재하지 않거나 삭제된 게시물입니다.", null));
        }
    }
}