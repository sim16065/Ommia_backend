package com.mia.community.service;

import com.mia.community.common.exception.CustomException;
import com.mia.community.common.exception.ErrorCode;
import com.mia.community.dto.post.request.PostCreateRequest;
import com.mia.community.dto.post.request.PostUpdateRequest;
import com.mia.community.dto.post.response.PostListResponse;
import com.mia.community.dto.post.response.PostResponse;
import com.mia.community.entity.Post;
import com.mia.community.entity.User;
import com.mia.community.repository.PostLikeRepository;
import com.mia.community.repository.PostRepository;
import com.mia.community.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostService {

    private  static final  int DEFAULT_PAGE_SIZE = 10;

    private final PostRepository postRepository;
    private final PostLikeRepository postLikeRepository;
    private final UserRepository userRepository;

    public PostService(PostRepository postRepository, PostLikeRepository postLikeRepository, UserRepository userRepository) {
        this.postRepository = postRepository;
        this.postLikeRepository = postLikeRepository;
        this.userRepository = userRepository;
    }

    public PostResponse createPost(Long userId, PostCreateRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        Post post = new Post(user, request.getTitle(), request.getContent(), request.getImageUrl());

        return toResponse(postRepository.save(post), userId);
    }

    // 게시글 목록 조회
    // 작성자 정보:  fetch join으로 User 함께 조회
    @Transactional(readOnly = true)
    public PostListResponse getAllPosts(int page) {
        Page<Post> pageResult = postRepository.findAllWithUser(PageRequest.of(page - 1, DEFAULT_PAGE_SIZE));

        List<PostResponse> data = pageResult.stream()
                .map(post -> toResponse(post, null))
                .collect(Collectors.toList());

        return new PostListResponse(
                data,
                page,
                DEFAULT_PAGE_SIZE,
                pageResult.getTotalElements(),
                pageResult.getTotalPages(),
                pageResult.hasNext()
        );
    }

     // 게시물 상세 조회: 조회 시 조회수 1 증가
    @Transactional
     public PostResponse getPost(Long postId, Long userId) {
         postRepository.increaseViewCount(postId);

         // 조회수 반영된 게시물 조회 후 반환
         Post post = postRepository.findById(postId)
                 .orElseThrow(() -> new CustomException(ErrorCode.POST_NOT_FOUND));

         return toResponse(post, userId);
     }

    // 게시물 수정
    @Transactional
    public PostResponse updatePost(Long postId, Long userId, PostUpdateRequest request) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new CustomException(ErrorCode.POST_NOT_FOUND));

        if (!post.getUser().getId().equals(userId)) {
            throw new CustomException(ErrorCode.POST_UPDATE_DENIED);
        }

        post.update(request.getTitle(), request.getContent(), request.getImageUrl());

        return toResponse(post, userId);
    }

    // 게시물 삭제
    @Transactional
    public void deletePost(Long postId, Long userId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new CustomException(ErrorCode.POST_NOT_FOUND));
        if (!post.getUser().getId().equals(userId)) {
            throw new CustomException(ErrorCode.POST_DELETE_DENIED);
        }

        postRepository.delete(post);
    }

    // Post 엔티티를 PostResponse DTO로 변환
    // 좋아요 여부는 Post 엔티티에 저장된 값이 아니므로 post_likes 테이블에서 별도로 조회
    private PostResponse toResponse(Post post, Long userId) {
        boolean isLiked = postLikeRepository.existsByPostIdAndUserId(post.getId(), userId); // 좋아요 여부 확인

        return new PostResponse(post, isLiked);
    }
}