package com.community.post.repository;

import com.community.post.domain.Post;

import java.util.List;
import java.util.Optional;

public interface PostRepository {
    Post save(Post post);

    Optional<Post> findById(Long postId);

    List<Post> findAll(int offset, int size);

    void deleteById(Long postId);
}
