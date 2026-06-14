package com.mia.community.service;

import com.mia.community.common.exception.CustomException;
import com.mia.community.common.exception.ErrorCode;
import com.mia.community.entity.Comment;
import com.mia.community.dto.comment.request.CommentCreateRequest;
import com.mia.community.dto.comment.request.CommentUpdateRequest;
import com.mia.community.dto.comment.response.CommentListResponse;
import com.mia.community.dto.comment.response.CommentResponse;
import com.mia.community.repository.CommentRepository;
import com.mia.community.repository.PostRepository;
import com.mia.community.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public CommentService(CommentRepository commentRepository, PostRepository postRepository, UserRepository userRepository) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    public CommentListResponse getComments(Long postId, Long cursor, int pageSize) {
        validatePostExists(postId);

        List<Comment> comments = cursor == null
                ? commentRepository.findFirstPageByPostId(postId, pageSize + 1)
                : commentRepository.findByPostIdAndCursor(postId, cursor, pageSize + 1);

        boolean hasNext = comments.size() > pageSize;
        if (hasNext) comments = comments.subList(0, pageSize);

        Long nextCursor = hasNext ? comments.getLast().getId() : null;

        List<CommentResponse> data = comments.stream()
                .map(CommentResponse::from)
                .collect(Collectors.toList());

        return new CommentListResponse(data, nextCursor, pageSize, hasNext);
    }

    public CommentResponse createComment(Long postId, Long userId, CommentCreateRequest request) {
        validatePostExists(postId);
        Comment comment = new Comment(postId, userId, request.getContent());

        return CommentResponse.from(commentRepository.save(comment));
    }

    public CommentResponse updateComment(Long postId, Long commentId, Long userId, CommentUpdateRequest request) {
        validatePostExists(postId);
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new CustomException(ErrorCode.COMMENT_NOT_FOUND));

        if (!comment.getPostId().equals(postId)) {
            throw new CustomException(ErrorCode.COMMENT_NOT_FOUND);
        }
        if (!comment.getUserId().equals(userId)) {
            throw new CustomException(ErrorCode.COMMENT_UPDATE_DENIED );
        }
        comment.update(request.getContent());
        return CommentResponse.from(commentRepository.save(comment));
    }

    public void deleteComment(Long postId, Long commentId, Long userId) {
        validatePostExists(postId);
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new CustomException(ErrorCode.COMMENT_NOT_FOUND));

        if (!comment.getPostId().equals(postId)) {
            throw new CustomException(ErrorCode.COMMENT_NOT_FOUND);
        }
        if (!comment.getUserId().equals(userId)) {
            throw new CustomException(ErrorCode.COMMENT_DELETE_DENIED );
        }
        commentRepository.deleteById(commentId);
    }

    private void validatePostExists(Long postId) {
        postRepository.findById(postId)
                .orElseThrow(() -> new CustomException(ErrorCode.POST_NOT_FOUND));

    }
}