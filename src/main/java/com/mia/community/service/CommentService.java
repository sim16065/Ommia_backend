package com.mia.community.service;

import com.mia.community.common.exception.CustomException;
import com.mia.community.common.exception.ErrorCode;
import com.mia.community.entity.Comment;
import com.mia.community.dto.comment.request.CommentCreateRequest;
import com.mia.community.dto.comment.request.CommentUpdateRequest;
import com.mia.community.dto.comment.response.CommentListResponse;
import com.mia.community.dto.comment.response.CommentResponse;
import com.mia.community.entity.User;
import com.mia.community.repository.CommentRepository;
import com.mia.community.repository.PostRepository;
import com.mia.community.repository.PostStatRepository;
import com.mia.community.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final PostStatRepository postStatRepository;
    private final UserRepository userRepository;

    public CommentService(CommentRepository commentRepository, PostRepository postRepository, PostStatRepository postStatRepository, UserRepository userRepository) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
        this.postStatRepository = postStatRepository;
        this.userRepository = userRepository;
    }

    @Transactional(readOnly= true)
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

    @Transactional
    public CommentResponse createComment(Long postId, Long userId, CommentCreateRequest request) {
        validatePostExists(postId);

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
        Comment comment = new Comment(postId, user, request.getContent());

        Comment saved = commentRepository.save(comment);
        postStatRepository.increaseCommentCount(postId);
        return CommentResponse.from(saved);
    }

    @Transactional
    public CommentResponse updateComment(Long postId, Long commentId, Long userId, CommentUpdateRequest request) {
        validatePostExists(postId);
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new CustomException(ErrorCode.COMMENT_NOT_FOUND));

        if (!comment.getPostId().equals(postId)) {
            throw new CustomException(ErrorCode.COMMENT_NOT_FOUND);
        }
        if (!comment.getUser().getId().equals(userId)) {
            throw new CustomException(ErrorCode.COMMENT_UPDATE_DENIED );
        }
        comment.update(request.getContent());
        commentRepository.flush();
        return CommentResponse.from(comment);
    }

    @Transactional
    public void deleteComment(Long postId, Long commentId, Long userId) {
        validatePostExists(postId);
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new CustomException(ErrorCode.COMMENT_NOT_FOUND));

        if (!comment.getPostId().equals(postId)) {
            throw new CustomException(ErrorCode.COMMENT_NOT_FOUND);
        }
        if (!comment.getUser().getId().equals(userId)) {
            throw new CustomException(ErrorCode.COMMENT_DELETE_DENIED );
        }
        commentRepository.delete(comment);
        postStatRepository.decreaseCommentCount(postId);
    }

    private void validatePostExists(Long postId) {
        postRepository.findById(postId)
                .orElseThrow(() -> new CustomException(ErrorCode.POST_NOT_FOUND));

    }
}