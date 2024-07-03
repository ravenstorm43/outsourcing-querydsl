package com.sparta.outsourcing.like.repository;

import com.sparta.outsourcing.like.entity.Like;
import com.sparta.outsourcing.like.entity.LikeType;

import java.util.Optional;

public interface LikeCustomRepository {
    Long countByContentIdAndContentType(Long contentId, LikeType contentType);
    Long countByUserIdAndContentType(Long userId, LikeType contentType);
    Optional<Like> findByUserIdAndContentIdAndContentType(Long userId, Long contentId, LikeType contentType);
}
