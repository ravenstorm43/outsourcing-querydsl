package com.sparta.outsourcing.follow.repository;

import com.sparta.outsourcing.follow.entity.Follow;

import java.util.Optional;

public interface FollowCustomRepository {
    Optional<Follow> findByUserIdAndFollowUserId(Long userId, Long followUserId);
}
