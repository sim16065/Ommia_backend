package com.community.comment.service;

import com.community.comment.domain.Comment;
import com.community.comment.dto.request.CommentCreateRequest;
import com.community.comment.dto.request.CommentUpdateRequest;
import com.community.comment.dto.response.CommentResponse;
import com.community.comment.repository.CommentRepository;
import com.community.post.repository.PostRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    public CommentService(CommentRepository commentRepository, PostRepository postRepository) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
    }

    public List<CommentResponse> getComments(Long postId) {
        validatePostExists(postId);

        return commentRepository.findAllByPostId(postId)
                .stream()
                .map(CommentResponse::from)
                .collect(Collectors.toList());
    }

    public CommentResponse createComment(Long postId, CommentCreateRequest request) {
        validatePostExists(postId);

        Comment comment = new Comment(
                postId,
                request.getContent(),
                request.getAuthor()
        );

        Comment savedComment = commentRepository.save(comment);

        return CommentResponse.from(savedComment);
    }

    public CommentResponse updateComment(Long postId, Long commentId, CommentUpdateRequest request) {
        validatePostExists(postId);

        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("댓글을 찾을 수 없습니다."));

        if (!comment.getPostId().equals(postId)) {
            throw new IllegalArgumentException("댓글을 찾을 수 없습니다.");
        }

        comment.update(request.getContent());

        Comment updatedComment = commentRepository.save(comment);

        return CommentResponse.from(updatedComment);
    }

    public void deleteComment(Long postId, Long commentId) {
        validatePostExists(postId);

        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("댓글을 찾을 수 없습니다."));

        if (!comment.getPostId().equals(postId)) {
            throw new IllegalArgumentException("댓글을 찾을 수 없습니다.");
        }

        commentRepository.deleteById(commentId);
    }

    private void validatePostExists(Long postId) {
        postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("게시물을 찾을 수 없습니다."));
    }
}