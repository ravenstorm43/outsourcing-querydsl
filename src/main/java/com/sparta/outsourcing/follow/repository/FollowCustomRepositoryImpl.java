package com.sparta.outsourcing.follow.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.outsourcing.follow.entity.Follow;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

import static com.sparta.outsourcing.follow.entity.QFollow.follow;


@RequiredArgsConstructor
public class FollowCustomRepositoryImpl implements FollowCustomRepository{
    private final JPAQueryFactory jpaQueryFactory;
    @Override
    public Optional<Follow> findByUserIdAndFollowUserId(Long userId, Long followUserId) {
        return Optional.ofNullable(jpaQueryFactory.selectFrom(follow)
                .where(
                        follow.user.id.eq(userId),
                        follow.followedUser.id.eq(followUserId)
                )
                .fetchOne());
    }
}
