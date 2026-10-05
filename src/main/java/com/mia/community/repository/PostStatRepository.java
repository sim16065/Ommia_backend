package com.mia.community.repository;

import com.mia.community.entity.PostStat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface PostStatRepository extends JpaRepository<PostStat, Long> {

    @Modifying(clearAutomatically = true)
    @Query("update PostStat ps set ps.viewCount = ps.viewCount + 1 where ps.postId = :postId")
    void increaseViewCount(Long postId);

    @Modifying
    @Query("update PostStat ps set ps.likeCount = ps.likeCount + 1 where ps.postId = :postId")
    void increaseLikeCount(Long postId);

    @Modifying
    @Query("update PostStat ps set ps.likeCount = ps.likeCount - 1 where ps.postId = :postId and ps.likeCount > 0")
    void decreaseLikeCount(Long postId);

    @Query("select ps.likeCount from PostStat ps where ps.postId = :postId")
    long findLikeCountByPostId(Long postId);
}
