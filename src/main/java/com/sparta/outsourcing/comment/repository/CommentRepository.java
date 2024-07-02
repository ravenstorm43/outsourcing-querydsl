package com.sparta.outsourcing.comment.repository;

import com.sparta.outsourcing.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long>, CommentCustomRepository {
    List<Comment> findAllByBoardId(Long boardId);
}
