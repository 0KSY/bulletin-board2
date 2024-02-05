package com.solo.bulletinboard2.comment.service;

import com.solo.bulletinboard2.comment.entity.Comment;
import com.solo.bulletinboard2.comment.repository.CommentRepository;
import com.solo.bulletinboard2.exception.BusinessLogicException;
import com.solo.bulletinboard2.exception.ExceptionCode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@Transactional
public class CommentService {

    private final CommentRepository commentRepository;

    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    public Comment findVerifiedComment(long commentId){
        Optional<Comment> optionalComment = commentRepository.findById(commentId);

        Comment foundComment = optionalComment
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.COMMENT_NOT_FOUND));

        return foundComment;
    }

    public Comment createComment(Comment comment){
        return commentRepository.save(comment);
    }

    public Comment updateComment(Comment comment){
        Comment foundComment = findVerifiedComment(comment.getCommentId());

        Optional.ofNullable(comment.getContent())
                .ifPresent(content -> foundComment.setContent(content));

        foundComment.setModifiedAt(LocalDateTime.now());

        return commentRepository.save(foundComment);
    }

}
