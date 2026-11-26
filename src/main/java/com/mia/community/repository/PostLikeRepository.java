package com.mia.community.repository;

import com.mia.community.entity.PostLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PostLikeRepository extends JpaRepository<PostLike, PostLike.PostLikeId> {
    boolean existsByPostIdAndUserId(Long postId, Long userId);

    @Modifying
    @Query("DELETE FROM PostLike pl WHERE pl.postId = :postId")
    void deleteByPostId(@Param("postId") Long postId);
}
