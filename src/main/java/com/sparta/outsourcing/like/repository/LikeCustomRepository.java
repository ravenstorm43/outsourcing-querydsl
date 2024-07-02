package com.sparta.outsourcing.like.repository;

import com.sparta.outsourcing.like.entity.Like;
import com.sparta.outsourcing.like.entity.LikeType;

import java.util.Optional;

public interface LikeCustomRepository {
    int countByContentIdAndContentType(Long contentId, LikeType contentType);
    Optional<Like> findByUserIdAndContentIdAndContentType(Long userId, Long contentId, LikeType contentType);
}
