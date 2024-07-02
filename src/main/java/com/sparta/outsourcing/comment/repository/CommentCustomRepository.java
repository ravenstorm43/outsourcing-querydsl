package com.sparta.outsourcing.comment.repository;

import com.sparta.outsourcing.comment.entity.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CommentCustomRepository {
    Page<Comment> findAllByLikedUserIds(Long userId, Pageable pageable);
}
