package com.sparta.outsourcing.like.repository;

import com.sparta.outsourcing.like.entity.Like;
import com.sparta.outsourcing.like.entity.LikeType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikeRepository extends JpaRepository<Like, Long> {
    Optional<Like> findByUserIdAndContentIdAndContentType(Long userId, Long contentId, LikeType contentType);
    int countByContentIdAndContentType(Long contentId, LikeType contentType);
}
