package com.mia.community.repository;

import com.mia.community.entity.PostLike;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostLikeRepository extends JpaRepository<PostLike, PostLike.PostLikeId> {
    boolean existsByPostIdAndUserId(Long postId, Long userId);
}
