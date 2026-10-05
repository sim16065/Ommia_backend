package com.mia.community.service;

import com.mia.community.common.exception.CustomException;
import com.mia.community.common.exception.ErrorCode;
import com.mia.community.dto.postlike.response.PostLikeResponse;
import com.mia.community.entity.PostLike;
import com.mia.community.repository.PostLikeRepository;
import com.mia.community.repository.PostRepository;
import com.mia.community.repository.PostStatRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PostLikeService {
    private final PostLikeRepository postLikeRepository;
    private final PostStatRepository postStatRepository;
    private final PostRepository postRepository;

    public PostLikeService(PostLikeRepository postLikeRepository, PostStatRepository postStatRepository,
                           PostRepository postRepository) {
        this.postLikeRepository = postLikeRepository;
        this.postStatRepository = postStatRepository;
        this.postRepository = postRepository;
    }

    // 게시물 좋아요 추가
    @Transactional
    public PostLikeResponse addLike(Long postId, Long userId) {
        validatePostExists(postId);

        if (postLikeRepository.existsByPostIdAndUserId(postId, userId)) {
            throw new CustomException(ErrorCode.ALREADY_LIKED_POST);
        }

        postLikeRepository.save(new PostLike(userId, postId));
        postStatRepository.increaseLikeCount(postId);

        return new PostLikeResponse(true, getLikeCount(postId));
    }

    // 게시물 좋아요 삭제
    @Transactional
    public PostLikeResponse removeLike(Long postId, Long userId) {
        validatePostExists(postId);

        if (!postLikeRepository.existsByPostIdAndUserId(postId, userId)) {

            return new PostLikeResponse(false, getLikeCount(postId));
        }

        postLikeRepository.deleteById(new PostLike.PostLikeId(userId, postId));
        postStatRepository.decreaseLikeCount(postId);

        return new PostLikeResponse(false, getLikeCount(postId));
    }

    // 게시물 존재 여부
    private void validatePostExists(Long postId) {
        if (!postRepository.existsById(postId)) {
            throw new CustomException(ErrorCode.POST_NOT_FOUND);
        }
    }

    // 게시물 좋아요 카운트
    private long getLikeCount (Long postId) {
        return postStatRepository.findLikeCountByPostId(postId);
    }
}
