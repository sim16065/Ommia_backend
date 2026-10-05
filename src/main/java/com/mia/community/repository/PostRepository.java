package com.mia.community.repository;

import com.mia.community.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {

    @Query(
            value = "select p from Post p join fetch p.user",
            countQuery = "select count(p) from Post p"
    )
    Page<Post> findAllWithUser(Pageable pageable);

    @Query("""
    select p
    from Post p
    join fetch p.user
    join fetch p.stats
    where p.id = :postId
""")
    Optional<Post> findByIdWithUserAndStats(Long postId);

    @Query(
            value = """
        select p
        from Post p
        join fetch p.user
        join fetch p.stats
    """,
            countQuery = "select count(p) from Post p"
    )
    Page<Post> findAllWithUserAndStats(Pageable pageable);
}
