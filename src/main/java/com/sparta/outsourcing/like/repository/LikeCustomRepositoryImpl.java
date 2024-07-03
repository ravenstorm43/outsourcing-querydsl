package com.sparta.outsourcing.like.repository;

import com.querydsl.core.types.dsl.Wildcard;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.outsourcing.like.entity.Like;
import com.sparta.outsourcing.like.entity.LikeType;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

import static com.sparta.outsourcing.like.entity.QLike.like;

@RequiredArgsConstructor
public class LikeCustomRepositoryImpl implements LikeCustomRepository {
    private final JPAQueryFactory jpaQueryFactory;
    @Override
    public Long countByContentIdAndContentType(Long contentId, LikeType contentType) {
        return jpaQueryFactory.select(Wildcard.count)
                .from(like)
                .where(
                    like.contentId.eq(contentId),
                    like.contentType.eq(contentType)
                )
                .fetch().get(0);
    }

    @Override
    public Long countByUserIdAndContentType(Long userId, LikeType contentType) {
        return jpaQueryFactory.select(Wildcard.count)
                .from(like)
                .where(
                        like.userId.eq(userId),
                        like.contentType.eq(contentType)
                )
                .fetch().get(0);
    }

    @Override
    public Optional<Like> findByUserIdAndContentIdAndContentType(Long userId, Long contentId, LikeType contentType) {
        return Optional.ofNullable(jpaQueryFactory.selectFrom(like)
                .where(
                        like.userId.eq(userId),
                        like.contentId.eq(contentId),
                        like.contentType.eq(contentType)
                )
                .fetchOne());
    }
}
