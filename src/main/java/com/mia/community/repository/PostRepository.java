package com.mia.community.repository;

import com.mia.community.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface PostRepository extends JpaRepository<Post, Long> {

    @Query(
            value = "select p from Post p join fetch p.user",
            countQuery = "select count(p) from Post p"
    )
    Page<Post> findAllWithUser(Pageable pageable);

    // 쿼리 실행 후 영속성 컨텍스트를 비워 DB 최신 조회수 가져오기
    @Modifying(clearAutomatically = true)
    @Query("update Post p set p.viewCount = p.viewCount + 1 where p.id = :postId")
    void increaseViewCount(Long postId);

    @Modifying
    @Query("update Post p set p.likeCount = p.likeCount + 1 where p.id = :postId")
    void increaseLikeCount(Long postId);

    @Modifying
    @Query("update Post p set p.likeCount = p.likeCount - 1 where p.id = :postId and p.likeCount > 0")
    void decreaseLikeCount(Long postId);
}
