package com.mia.community.repository;

import com.mia.community.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    // 첫 페이지
    @Query("SELECT c FROM Comment c JOIN FETCH c.user WHERE c.post.id = :postId ORDER BY c.id ASC LIMIT :pageSize")
    List<Comment> findFirstPageByPostId(@Param("postId") Long postId, @Param("pageSize") int pageSize);

    // 다음 페이지
    @Query("SELECT c FROM Comment c JOIN FETCH c.user WHERE c.post.id = :postId AND c.id > :cursor ORDER BY c.id ASC LIMIT :pageSize")
    List<Comment> findByPostIdAndCursor(@Param("postId") Long postId, @Param("cursor") Long cursor, @Param("pageSize") int pageSize);

    @Modifying(clearAutomatically = true)
    @Query("DELETE FROM Comment c WHERE c.post.id = :postId")
    void deleteByPostId(@Param("postId") Long postId);
}
