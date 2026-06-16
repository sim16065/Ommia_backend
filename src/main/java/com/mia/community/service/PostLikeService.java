package com.mia.community.service;

import com.mia.community.common.exception.CustomException;
import com.mia.community.common.exception.ErrorCode;
import com.mia.community.dto.postlike.response.PostLikeResponse;
import com.mia.community.entity.PostLike;
import com.mia.community.repository.PostLikeRepository;
import com.mia.community.repository.PostRepository;
import org.springframework.stereotype.Service;

@Service
public class PostLikeService {
    private final PostLikeRepository postLikeRepository;
    private final PostRepository postRepository;

    public PostLikeService(PostLikeRepository postLikeRepository,
                           PostRepository postRepository) {
        this.postLikeRepository = postLikeRepository;
        this.postRepository = postRepository;
    }

    // 게시물 좋아요 추가
    public PostLikeResponse addLike(Long postId, Long userId) {
        if (!postRepository.existsById(postId)) {
            throw new CustomException(ErrorCode.POST_NOT_FOUND);
        }

        if (postLikeRepository.existsByPostIdAndUserId(postId, userId)) {
            throw new CustomException(ErrorCode.ALREADY_LIKED_POST);
        }

        PostLike postLike = new PostLike(userId, postId);
        postLikeRepository.save(postLike);

        long likeCount = postLikeRepository.countByPostId(postId);

        return new PostLikeResponse(true, likeCount);
    }

    public PostLikeResponse removeLike(Long postId, Long userId) {
        if (!postRepository.existsById(postId)) {
            throw new CustomException(ErrorCode.POST_NOT_FOUND);
        }

        postLikeRepository.deleteById(new PostLike.PostLikeId(userId, postId));

        long likeCount = postLikeRepository.countByPostId(postId);

        return new PostLikeResponse(false, likeCount);
    }
}