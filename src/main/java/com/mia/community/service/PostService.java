package com.community.post.service;

import com.community.post.domain.Post;
import com.community.post.dto.request.PostCreateRequest;
import com.community.post.dto.request.PostUpdateRequest;
import com.community.post.dto.response.PostResponse;
import com.community.post.repository.PostRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostService {

    private final PostRepository postRepository;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public PostResponse createPost(PostCreateRequest request) {
        Post post = new Post(
                request.getTitle(),
                request.getContent(),
                request.getAuthor()
        );

        Post savedPost = postRepository.save(post);

        return PostResponse.from(savedPost);
    }

    public List<PostResponse> getAllPosts(int page, int size) {
        int offset = (page - 1) * size;
        List<Post> posts = postRepository.findAll(offset, size);

        return posts.stream()
                .map(PostResponse::from)
                .collect(Collectors.toList());
    }

    public PostResponse getPost(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("게시물을 찾을 수 없습니다."));

        return PostResponse.from(post);
    }

    public PostResponse updatePost(Long postId, PostUpdateRequest request) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("게시물을 찾을 수 없습니다."));

        post.update(request.getTitle(), request.getContent());

        Post updatedPost = postRepository.save(post);

        return PostResponse.from(updatedPost);
    }

    public void deletePost(Long postId) {
        postRepository.deleteById(postId);
    }
}