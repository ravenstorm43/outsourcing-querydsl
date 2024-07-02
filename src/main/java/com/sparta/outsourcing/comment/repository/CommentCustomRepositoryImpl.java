package com.sparta.outsourcing.comment.repository;

import com.querydsl.core.types.dsl.Wildcard;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.outsourcing.comment.entity.Comment;
import com.sparta.outsourcing.like.entity.LikeType;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

import java.util.List;

import static com.sparta.outsourcing.comment.entity.QComment.comment1;
import static com.sparta.outsourcing.like.entity.QLike.like;

@RequiredArgsConstructor
public class CommentCustomRepositoryImpl implements CommentCustomRepository {
    private final JPAQueryFactory jpaQueryFactory;
    @Override
    public Page<Comment> findAllByLikedUserIds(Long userId, Pageable pageable) {
        List<Comment> commentList = jpaQueryFactory.selectFrom(comment1)
                .where(
                        comment1.id.in(jpaQueryFactory
                                .select(like.contentId).from(like)
                                .where(like.userId.eq(userId), like.contentType.eq(LikeType.COMMENT))
                                .fetch())
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long totalSize = jpaQueryFactory.select(Wildcard.countAsInt)
                .from(comment1)
                .where(
                        comment1.id.in(jpaQueryFactory
                                .select(like.contentId).from(like)
                                .where(like.userId.eq(userId), like.contentType.eq(LikeType.COMMENT))
                                .fetch())
                )
                .fetch().get(0);
        return PageableExecutionUtils.getPage(commentList, pageable, () -> totalSize);
    }
}
