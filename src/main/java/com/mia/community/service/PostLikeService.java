package com.mia.community.service;

import com.mia.community.common.exception.CustomException;
import com.mia.community.common.exception.ErrorCode;
import com.mia.community.dto.postlike.response.PostLikeResponse;
import com.mia.community.entity.Post;
import com.mia.community.entity.PostLike;
import com.mia.community.repository.PostLikeRepository;
import com.mia.community.repository.PostRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    @Transactional
    public PostLikeResponse addLike(Long postId, Long userId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new CustomException(ErrorCode.POST_NOT_FOUND));

        if (postLikeRepository.existsByPostIdAndUserId(postId, userId)) {
            throw new CustomException(ErrorCode.ALREADY_LIKED_POST);
        }

        postLikeRepository.save(new PostLike(userId, postId));
        postRepository.increaseLikeCount(postId);

        long likeCount = post.getLikeCount() + 1;

        return new PostLikeResponse(true, likeCount);
    }

    @Transactional
    public PostLikeResponse removeLike(Long postId, Long userId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new CustomException(ErrorCode.POST_NOT_FOUND));

        if (!postLikeRepository.existsByPostIdAndUserId(postId, userId)) {
            return new PostLikeResponse(false, post.getLikeCount());
        }

        postLikeRepository.deleteById(new PostLike.PostLikeId(userId, postId));
        postRepository.decreaseLikeCount(postId);

        long likeCount = post.getLikeCount() - 1;

        return new PostLikeResponse(false, likeCount);
    }
}