package com.sparta.outsourcing.comment.repository;

import com.sparta.outsourcing.comment.entity.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long>, CommentCustomRepository {
    Page<Comment> findAllByBoardId(Long boardId, Pageable pageable);
}
