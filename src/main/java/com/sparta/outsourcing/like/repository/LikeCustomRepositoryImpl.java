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
    public int countByContentIdAndContentType(Long contentId, LikeType contentType) {
        return jpaQueryFactory.select(Wildcard.countAsInt)
                .from(like)
                .where(
                    like.contentId.eq(contentId),
                    like.contentType.eq(contentType)
                )
                .fetchOne();
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
