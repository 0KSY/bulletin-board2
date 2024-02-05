package com.solo.bulletinboard2.comment.repository;

import com.solo.bulletinboard2.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
